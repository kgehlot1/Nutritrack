package com.fit2081.krishna_a3_34413111

object UserManager {

    private var _userId: String? = null
    val userId: String
        get() = _userId ?: throw IllegalStateException("User ID not set")

    fun setUserId(id: String) {
        _userId = id
    }

    fun clearUser() {
        _userId = null
    }

    fun isLoggedIn(): Boolean {
        return _userId != null
    }
}
