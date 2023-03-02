import app.AdvertApplication

fun main() {
    val app = AdvertApplication()

    app.loadUsersFromFile() // Подгружаем пользователей из файла
    app.loadAdsFromFile() // Подгружаем объявления из файла

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
                println("Введите логин и пароль")
                app.login(readln(), readln())
            }

            "view" -> {
                app.viewAllAds()
            }

            "add" -> {
                app.placeAd("Hello", "Hello", 340.55)
            }

            "buy" -> {
                print("Введите id товара -> ")
                app.buyAd(readln().toInt())
            }

            "logout" -> {
                app.logout()
            }

            "exit" -> {
                print("Программа завершила свою работу, нажмите любую клавишу...")
                break
            }

            else -> "Неизвестный ввод"
        }
    }
}