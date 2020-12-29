package pw.wpam.polityper

import pw.wpam.polityper.models.GameBetHeader
import pw.wpam.polityper.models.LeaderHeader


class DataSourceLeaderboard{

    companion object{

        fun createDataSet(): ArrayList<LeaderHeader>{
            val list = ArrayList<LeaderHeader>()
            list.add(
                LeaderHeader(
                    "Mirosław",
                    100
                )
            )
            list.add(
                LeaderHeader(
                    "Radosław",
                    90
                )
            )
            list.add(
                LeaderHeader(
                    "Waldemar",
                    80
                )
            )
            list.add(
                LeaderHeader(
                    "Krzysztof",
                    70
                )
            )
            list.add(
                LeaderHeader(
                    "ZdenekOndraszek",
                    60
                )
            )
            list.add(
                LeaderHeader(
                        "Carlitos",
                        40
                    )
            )
            list.add(
                LeaderHeader(
                    "Adaś Niezgódka",
                    30
                )
            )

            return list
        }
    }
}