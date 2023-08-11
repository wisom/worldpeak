package com.worldpeak.chnsmilead.home.model

data class AreaDynamicReponse(
        val adminArticleApps: List<DynamicItem>? = null,
        val adminPublicPictureApps: List<AdminPublicPictureApp>? = null
)

data class AdminPublicPictureApp(
        val imgUrl: String? = "",
        val linkUrl: String? = ""
)