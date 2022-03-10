package com.co.wewin.WeWinApplication

class AppConstants {
    object StorageUtilKeys{
        const val PREFERENCE_KEY = "general"
        const val USER_INFO_PREFERENCE_KEY = "user_info_preference_key"

        const val userToken = "user_token"
        const val userMobile = "user_mobile"
    }
    object CardTypeKeys{
        const val HEART = "hearts"
        const val CLUB = "clubs"
        const val DIAMOND = "diamonds"
        const val SPADE = "spades"

        const val hearts_temp="Hearts"
        const val clubs_temp="Clubs"
        const val dimonds_temp="Diamonds"
        const val spades_temp="Spades"
    }

    object AndarBaharKeys{
        const val game_id="61fa1cbe9aa9ad07df44e19a"
        const val game_name="andarbahar"
        const val andar = "andar"
        const val bahar = "bahar"
        const val tie = "tie"
        const val A_as_andar="A"
        const val B_as_bahar="B"
        const val T_as_tie="T"
    }

    object CrashKeys{
        const val game_id="61fa1cec9aa9ad07df44e19c"
        const val game_name="crash"
    }

    object DiceKeys{
        const val game_id="61fa1d059aa9ad07df44e1a2"
        const val game_name="dice"
    }
    object FastParityKeys{
        const val game_id="61fa1d239aa9ad07df44e1a4"
        const val game_name="fastParity"
        const val green="green"
        const val red="red"
        const val violet="violet"
        const val parity="Parity"
        const val parityOne="1"
        const val parityTwo="2"
        const val parityThree="3"
        const val parityFour="4"
        const val parityFive="5"
        const val paritySix="6"
        const val paritySeven="7"
        const val parityEight="8"
        const val parityNine="9"
        const val parityZero="0"
    }

    object SocketKeys{
        const val join="join"
        const val andarBahar="andarBahar"
        const val fastParity="fastParity"
    }
}