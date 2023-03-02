package app

class User(
    val id: Int,
    val login: String,
    val password: String,
    var balance: Double
) {
    override fun toString(): String {
        return "[Id пользователя - ${this.id}]\n" +
                "[Логин пользователя - ${this.login} | Пароль пользователя - ${this.password}]\n" +
                "[Баланс пользователя - ${this.balance}]\n"
    }
}
