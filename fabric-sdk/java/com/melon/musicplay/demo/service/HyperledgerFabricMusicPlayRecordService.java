package com.melon.musicplay.demo.service;

import com.melon.musicplay.demo.model.MusicInfo;
import com.melon.musicplay.demo.model.MusicPlayRecord;
import com.melon.musicplay.demo.model.MusicPlayRecordResponse;
import com.melon.musicplay.demo.model.MusicPlayRecordSearchRequest;
import com.melon.musicplay.demo.model.MusicPlayRecordSearchRequest.MusicPlayRecordSearchRequestWithDate;
import com.melon.musicplay.demo.model.MusicPlayTransaction;
import com.melon.musicplay.demo.utils.ChaincodeFunctions;
import static com.melon.musicplay.demo.utils.Util.wrappedFunction;

import lombok.extern.slf4j.Slf4j;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;

import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.BlockEvent.TransactionEvent;
import org.hyperledger.fabric.sdk.ChaincodeResponse.Status;

import java.util.Collection;
import java.util.Optional;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static com.melon.musicplay.demo.model.MusicPlayRecordResponse.EMPTY_MUSIC_PLAY_RESPONSE;
import static com.melon.musicplay.demo.model.MusicPlayTransaction.EMPTY_MUSIC_PLAY_TRANSACTION;

@Slf4j
public class HyperledgerFabricMusicPlayRecordService implements MusicPlayRecordBlockChainService {

    private final HFClient client;
    private final Channel channel;

    @Value("${hyperledger.chaincode.name}")
    private String melonMusicChaincodeName;
    private ObjectMapper objectMapper;

    public HyperledgerFabricMusicPlayRecordService(HFClient client, Channel channel) {
        this.client = client;
        this.channel = channel;
        objectMapper = createObjectMapper();
        Properties pr = channel.getServiceDiscoveryProperties();
        log.error("{}", pr);
        
    }

    private ObjectMapper createObjectMapper(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.disable(
            //DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, 
                        DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, 
                        DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        return mapper;
    }

    @Override
    public MusicPlayRecordResponse getMusicPlayRecordByMusicId(MusicPlayRecordSearchRequest searchRequest) {
        String musicId = searchRequest.getSearchKeyword();
        String pageSize = searchRequest.getPageSize();
        String bookmark = searchRequest.getBookmark();

        QueryByChaincodeRequest request =
                newQueryProposalRequest(ChaincodeFunctions.SEARCH_BY_MUSIC, musicId, pageSize, bookmark);

        return getChaincodeResponse(request, MusicPlayRecordResponse.class)
                .orElse(MusicPlayRecordResponse.EMPTY_MUSIC_PLAY_RESPONSE);
    }

    @Override
    public MusicPlayRecordResponse getMusicPlayRecordByMusicIdAndDate(MusicPlayRecordSearchRequestWithDate searchRequest){
        String musicId = searchRequest.getSearchKeyword();
        String dateFormat = searchRequest.getDate();
        String pageSize = searchRequest.getPageSize();
        String bookmark = searchRequest.getBookmark();

		QueryByChaincodeRequest request =
                newQueryProposalRequest(ChaincodeFunctions.SEARCH_BY_MUSIC_AND_DATE, musicId, pageSize, bookmark, dateFormat);

        return getChaincodeResponse(request, MusicPlayRecordResponse.class).orElse(EMPTY_MUSIC_PLAY_RESPONSE);
    }

    @Override
    public MusicPlayRecordResponse getMusicPlayRecordByUserId(MusicPlayRecordSearchRequest searchRequest) {
        String userId = searchRequest.getSearchKeyword();
        String pageSize = searchRequest.getPageSize();
        String bookmark = searchRequest.getBookmark();
        QueryByChaincodeRequest request = 
                newQueryProposalRequest(ChaincodeFunctions.SEARCH_BY_USER, userId, pageSize, bookmark);

        return getChaincodeResponse(request, MusicPlayRecordResponse.class).orElse(EMPTY_MUSIC_PLAY_RESPONSE);
    }

    @Override
    public MusicPlayRecordResponse getMusicPlayRecordByUserIdAndDate(MusicPlayRecordSearchRequestWithDate searchRequest) {
        String userId = searchRequest.getSearchKeyword();
        String dateFormat = searchRequest.getDate();
        String pageSize = searchRequest.getPageSize();
        String bookmark = searchRequest.getBookmark();
        
        QueryByChaincodeRequest request =
                newQueryProposalRequest(ChaincodeFunctions.SEARCH_BY_USER_AND_DATE
                                         , userId, pageSize, bookmark, dateFormat);

        return getChaincodeResponse(request, MusicPlayRecordResponse.class).orElse(EMPTY_MUSIC_PLAY_RESPONSE);
    }

    @Override
    public MusicPlayTransaction getMusicPlayTransaction(String musicRecordKey) {
        QueryByChaincodeRequest request = newQueryProposalRequest(ChaincodeFunctions.GET_TRANSACTION, musicRecordKey);

        return getChaincodeResponse(request, MusicPlayTransaction.class).orElse(EMPTY_MUSIC_PLAY_TRANSACTION);
	}

    private QueryByChaincodeRequest newQueryProposalRequest(String functionName, String... chaincodeParams) {
        QueryByChaincodeRequest request = client.newQueryProposalRequest();
        
        request.setChaincodeID(ChaincodeID.newBuilder().setName(melonMusicChaincodeName).build());
        request.setFcn(functionName).setArgs(chaincodeParams);

        return request;
    }
    
    private <T> Optional<T> getChaincodeResponse(QueryByChaincodeRequest request, Class<T> returnType){
        try{
            return deserializeQueryResponse(channel.queryByChaincode(request), returnType);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    private <T> Optional<T> deserializeQueryResponse(Collection<ProposalResponse> responses, Class<T> returnType){

        return responses.stream()
                        .map(response -> response.getMessage())
                        .map(message -> {
                            log.error("Transaction : {}", message);
                            return message;
                        })
                        .map(wrappedFunction(message -> objectMapper.readValue(message, returnType)))
                        .findAny();
    }
    @Override
    public void addMusicPlayRecord(MusicInfo musicInfo, String userId){
        String musicPlayRecordKey = getRandomId();
        MusicPlayRecord musicPlayRecord = new MusicPlayRecord(musicInfo, userId, musicPlayRecordKey);
        String jsonMusicPlayRecord= convertMusicPlayRecordToJson(musicPlayRecord);

        log.info("New music play record :: {}", jsonMusicPlayRecord);

        TransactionProposalRequest request =
            newTransactionProposalRequest(ChaincodeFunctions.ADD_MUSIC_PLAY_RECORD, musicPlayRecordKey, jsonMusicPlayRecord);
              
       sendTransactionProposalRequest(request);
    }

    private String convertMusicPlayRecordToJson(MusicPlayRecord musicPlayRecord) {
        try{
            return objectMapper.writeValueAsString(musicPlayRecord);
        }catch(JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }

    private String getRandomId() {
        return UUID.randomUUID().toString();
    }

    private void sendTransactionProposalRequest(TransactionProposalRequest request){
        try{
            Collection<ProposalResponse> proposalResponses = channel.sendTransactionProposal(request);
            log.error(">>>>>>>>>> Size : {}", proposalResponses.size());
            CompletableFuture<TransactionEvent> events = channel.sendTransaction(proposalResponses);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    private TransactionProposalRequest newTransactionProposalRequest(String functionName, String... chaincodeParams){
        TransactionProposalRequest request = client.newTransactionProposalRequest();
        request.setChaincodeID(ChaincodeID.newBuilder().setName(melonMusicChaincodeName).build());
        request.setFcn(functionName).setArgs(chaincodeParams);

        return request;
    }

    private boolean isResponseSuccess(Collection<ProposalResponse> responses){
        return responses.stream()
                .allMatch(response -> response.getStatus() == Status.SUCCESS);
    }
}
