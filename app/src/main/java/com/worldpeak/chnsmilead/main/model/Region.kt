package com.worldpeak.chnsmilead.main.model

class Region {
    //    {
    //        "account": "18675431289",
    //            "schoolName": "上海复兴中学",
    //            "studentId": "121231312",
    //            "deployType": "1",
    //            "deployServerName": "私有服务器001",
    //            "hostUrl": "http://121.199.18.201/",
    //            "hostUrl1": "http://127.0.0.0.1:84",
    //            "onlineState": "1"
    //    }
    @JvmField
    var account: String? = null
    var schoolName: String? = null
    @JvmField
    var studentId: String? = null
    var deployType: String? = null
    var deployServerName: String? = null
    @JvmField
    var hostUrl: String? = null
    @JvmField
    var hostUrl1: String? = null
    @JvmField
    var onlineState: String? = null
}