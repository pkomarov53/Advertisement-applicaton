import app.AdvertApplication

fun main() {
    val app = AdvertApplication()

    app.loadUsersFromFile()
    app.loadAdsFromFile()

    println("Меню программы:\n" +
            "\tlogin - Войти в приложение\n" +
            "\tview - Просмотреть объявления\n" +
            "\tadd - Добавить свое объявление\n" +
            "\tbuy - Купить товар из объявления\n" +
            "\tlogout - Выйти из приложения\n" +
            "\texit - Заврешить работу программы\n")

    while (true) {
        print("Введите действие -> ")
        when (readln().lowercase()) {
            "login" -> {
                println("Введите логин и пароль:")
                app.login(readln(), readln())
            }

            "view" -> {
                app.viewAllAds()
            }

            "add" -> {
                println("Введите название, описание и цену товара в объявлении")
                app.placeAd(readln(), readln(), readln().toDouble())
            }

            "buy" -> {
                print("Введите id товара -> ")
                app.buyAd(readln().toInt())
            }

            "logout" -> {
                app.logout()
            }

            "exit" -> {
                print("Программа завершила свою работу...")
                break
            }

            else -> {
                println("[Неизвестный ввод]\n")
            }
        }
    }
    app.loadAdsToFile()
}