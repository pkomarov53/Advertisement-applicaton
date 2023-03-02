package app

import java.io.File
import java.time.LocalDateTime

class AdvertApplication {

    private val ads = mutableListOf<Advert>()
    private val users = mutableListOf<User>()

    fun loadUsersFromFile() {
        val file = File("C:\\Users\\USER-HOME\\Documents\\GitHub\\Advert\\src\\main\\kotlin\\users.txt")
        if (!file.exists()) {
            throw IllegalArgumentException("Файл не существует")
        }

        file.forEachLine { line ->
            val fields = line.split(",")
            val id = fields[0].toInt()
            val login = fields[1]
            val password = fields[2]
            val balance = fields[3].toDouble()
            val user = User(id, login, password, balance)
            users.add(user)
        }
    }

    fun loadAdsFromFile() {
        val file = File("C:\\Users\\USER-HOME\\Documents\\GitHub\\Advert\\src\\main\\kotlin\\adverts.txt")
        if (!file.exists()) {
            throw IllegalArgumentException("Файл не существует")
        }

        file.forEachLine { line ->
            val fields = line.split(",")
            val id = fields[0].toInt()
            val title = fields[1]
            val userId = fields[2].toInt()
            val description = fields[3]
            val price = fields[4].toDouble()
            val publicationDate = LocalDateTime.now()

            val ad = Advert(id, title, userId, description, price, publicationDate)
            ads.add(ad)
        }
    }

    // текущий авторизованный пользователь
    private var currentUser: User? = null

    // вход в приложение
    fun login(login: String, password: String): Boolean {
        // поиск пользователя по логину и паролю
        val user = users.find { it.login == login && it.password == password }
        if (user != null) {
            currentUser = user
            return true
        }
        return false
    }

    // выход из приложения
    fun logout() {
        currentUser = null
    }

    // просмотр всех объявлений
    fun viewAllAds() {
        for (ad in ads) {
            if (ad.userId != currentUser?.id) {
                println(ad.toString())
            }
        }
    }

    // размещение объявления
    fun placeAd(title: String, description: String, price: Double) {
        if (currentUser == null) {
            println("Необходимо авторизоваться в приложении для размещения объявления")
            return
        }
        val ad = Advert(
            id = ads.size + 1,
            title = title,
            userId = currentUser!!.id,
            description = description,
            price = price,
            publishDate = LocalDateTime.now(),
            status = Advert.AdStatus.OPEN
        )
        ads.add(ad)
        println("Объявление успешно размещено")
    }

    // покупка товара из объявления
    fun buyAd(adId: Int) {
        if (currentUser == null) {
            println("Необходимо авторизоваться в приложении для покупки товара")
            return
        }
        val ad = ads.find { it.id == adId && it.status == Advert.AdStatus.OPEN }
        if (ad == null) {
            println("Объявление не найдено или уже закрыто")
            return
        }
        val seller = users.find { it.id == ad.userId }
        if (seller == null) {
            println("Произошла ошибка при покупке товара. Попробуйте еще раз")
            return
        }
        if (currentUser!!.balance < ad.price) {
            println("У вас недостаточно средств для покупки этого товара")
            return
        }
        currentUser!!.balance -= ad.price
        seller.balance += ad.price
        ad.status = Advert.AdStatus.CLOSED
        println("Покупка товара успешно завершена")
    }
}
