package app

import java.time.LocalDateTime

class Advert(
    val id: Int,
    val title: String,
    val userId: Int,
    val description: String,
    val price: Double,
    val publishDate: LocalDateTime,
    var status: AdStatus = AdStatus.OPEN
) {

    enum class AdStatus {
        OPEN,
        CLOSED
    }
}
