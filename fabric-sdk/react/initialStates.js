const InitialStates= {
    musics : [
        {
            id:1, 
            title:"Done For Me", 
            singers:["펀치 (Punch)"], 
            album:"호텔 델루나 OST Part.12",
            image:"/static/image/music/hotel12.png"
        },
        {
            id:2, 
            title:"안녕", 
            singers:["폴킴"], 
            album:"호텔 델루나 OST Part.10",
            image:"/static/image/music/hotel10.png"
        },
        {
            id:3, 
            title:"기억해줘요 내 모든 날과 그 때를", 
            singers:["거미"], 
            album:"호텔 델루나 OST Part.7",
            image:"/static/image/music/hotel7.png"
        },
        {
            id:4, 
            title:"그대라는 시", 
            singers:["태연"], 
            album:"호텔 델루나 OST Part.3",
            image:"/static/image/music/hotel3.png"
        },
        {
            id:5, 
            title:"내 목소리 들리니", 
            singers:["벤"], 
            album:"호텔 델루나 OST Part.9",
            image:"/static/image/music/hotel9.png"
        },
        {
            id:6, 
            title:"오늘도 빛나는 너에게(To You My Light)(Feat.이라온)", 
            singers:["마크툽(MAKTUB)"], 
            album:"Read Moon : To You My Light",
            image:"/static/image/music/mark.png"
        },
        {
            id:7, 
            title:"내 맘을 볼 수 있나요", 
            singers:["헤이즈(Heize)"], 
            album:"호텔 델루나 OST Part.5",
            image:"/static/image/music/hotel5.png"
        },
        {
            id:8, 
            title:"니 소식", 
            singers:["송하예"], 
            album:"니 소식",
            image:"/static/image/music/nisosick.png"
        }
    ],
    page : 1,
    date : new Date(),
    searchKeyword : '',
    bookmarks : [''],
    currentRecordsPage : 0,
    previousButtonDisabled : true,
    nextButtonDisabled : true,
    isModalShow : false,
    transactionInfo : {}
}

export default InitialStates