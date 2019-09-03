package com.melon.musicplay.demo;

import java.io.File;
import java.util.List;

import com.melon.musicplay.demo.service.AccountService;
import com.melon.musicplay.demo.service.HyperledgerFabricMusicPlayRecordService;
import com.melon.musicplay.demo.service.MusicPlayRecordBlockChainService;
import com.melon.musicplay.demo.utils.MusicPlayRecordSearchArgumentResolver;
import static com.melon.musicplay.demo.utils.Util.defaultCryptoSuite;

import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.NetworkConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Configuration
@Slf4j
public class DemoApplication implements WebMvcConfigurer{

	@Autowired
    private AccountService accountService;
    @Autowired
    private MusicPlayRecordSearchArgumentResolver musicPlayRecordSearchArgumentResolver;
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(musicPlayRecordSearchArgumentResolver);
    }

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry){
		registry.addResourceHandler("/static/**")
				.addResourceLocations("classpath:/static/");
	}

	@Bean
	public MusicPlayRecordBlockChainService hyperledgerMusicPlayRecordService(){
		HFClient client = createHFClient();

		HyperledgerFabricMusicPlayRecordService service = 
			new HyperledgerFabricMusicPlayRecordService(client, createMusicChannel(client));
		
		return service;
	}

    private HFClient createHFClient() {
        HFClient newClient = HFClient.createNewInstance();
        try {
            newClient.setCryptoSuite(defaultCryptoSuite());
            newClient.setUserContext(accountService.getAdminContext());
            return newClient;
        } catch (Exception e) {
            throw new RuntimeException("Exception occured while trying to create HFClient instance", e);
        }
    }
	
    private Channel createMusicChannel(HFClient client) {
        try {
            Channel channel = client.loadChannelFromConfig("melonchannel",
                    getNetworkConfig("network-config.yaml"));
            channel.initialize();
            return channel;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
	}

    private NetworkConfig getNetworkConfig(String fileName) {
        try {
            return NetworkConfig.fromYamlFile(getChannelConfigFile(fileName));
        } catch (Exception e) {
            throw new RuntimeException("Exception occured while trying to create network configuration", e);
        }
    }

    private File getChannelConfigFile(String fileName) {
        return new File("/Users/kakao/IdeaProjects/client/src/main/resources/" + fileName);
    }
}
