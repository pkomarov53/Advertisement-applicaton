package app

class Advert(
    val id: Int,
    val title: String,
    val userId: Int,
    val description: String,
    val price: Double,
    val publishDate: String,
    var status: AdStatus = AdStatus.OPEN
) {

    enum class AdStatus {
        OPEN,
        CLOSED
    }

    override fun toString(): String {
        return "[Id объявления - ${this.id}]\n" +
                "[Название объявления - ${this.title} | Описание объявления - ${this.description}]\n" +
                "[Id пользователя, разместившего объявление - ${this.userId} | Дата размещения объявления - ${this.publishDate}]\n" +
                "[Цена указанная в объявлении - ${this.price} | Статус объявления - ${this.status}]\n"
    }
}
