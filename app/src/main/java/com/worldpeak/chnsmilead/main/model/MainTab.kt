package com.worldpeak.chnsmilead.main.model

enum class MainTab {
    HOME, COMMUNITY, OA, CONTACT, MINE;

    companion object {

        @JvmStatic
        fun getTab(value: Int): MainTab {
            when (value) {
                1 -> {
                    return COMMUNITY
                }
                2 -> {
                    return OA
                }
                3 -> {
                    return CONTACT
                }
                4 -> {
                    return MINE
                }
                else -> {
                    return HOME
                }
            }
        }
    }
}