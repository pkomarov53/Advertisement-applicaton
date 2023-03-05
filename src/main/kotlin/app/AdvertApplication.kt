package app

import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AdvertApplication {

    private val ads = mutableListOf<Advert>()
    private val users = mutableListOf<User>()

    // переменная хранящая текущую дату и время в виде строки
    private val curDate = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss").format(
        LocalDateTime.parse(
            LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss")),
                    DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss")))

    // загружаем пользователей из файла
    fun loadUsersFromFile() {
        val file = File("files/users.txt")
        if (!file.exists()) {
            throw IllegalArgumentException("Файл не существует\n")
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

    // загружаем объявления из файла
    fun loadAdsFromFile() {
        val file = File("files/adverts.txt")
        if (!file.exists()) {
            throw IllegalArgumentException("Файл не существует\n")
        }

        file.forEachLine { line ->
            val fields = line.split(",")
            val id = fields[0].toInt()
            val title = fields[1]
            val userId = fields[2].toInt()
            val description = fields[3]
            val price = fields[4].toDouble()
            val date = fields[5]
            val ad = Advert(id, title, userId, description, price, date)
            ads.add(ad)
        }
    }

    // загружаем объявления в файл
    fun loadAdsToFile() {
        val file = File("files/adverts.txt")
        file.writeText("")

        ads.forEach {
            file.appendText(
                "${it.id},${it.title}," +
                        "${it.userId},${it.description}," +
                        "${it.price},${it.publishDate}," +
                        "${it.status}\n"
            )
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
            println("Вход успешно выполнен\n")
            return true
        }
        println("Ошибка в логине или пароле\n")
        return false
    }

    // выход из приложения
    fun logout() {
        if (currentUser == null) {
            println("Вы не авторизовались\n")
            return
        } else {
            println("Вы успешно вышли из аккаунта\n")
            currentUser = null
        }
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
            publishDate = curDate,
            status = Advert.AdStatus.OPEN
        )
        ads.add(ad)
        println("Объявление успешно размещено\n")
    }

    // покупка товара из объявления
    fun buyAd(adId: Int) {
        if (currentUser == null) {
            println("Необходимо авторизоваться в приложении для покупки товара\n")
            return
        }
        val ad = ads.find { it.id == adId && it.status == Advert.AdStatus.OPEN }
        if (ad == null) {
            println("Объявление не найдено или уже закрыто\n")
            return
        }
        val seller = users.find { it.id == ad.userId }
        if (seller == null) {
            println("Произошла ошибка при покупке товара. Попробуйте еще раз\n")
            return
        }
        if (currentUser!!.balance < ad.price) {
            println("У вас недостаточно средств для покупки этого товара\n")
            return
        }
        currentUser!!.balance -= ad.price
        seller.balance += ad.price
        ad.status = Advert.AdStatus.CLOSED
        println("Покупка товара успешно завершена\n")
    }
}
