package com.example.pendaftaranpondokpesantrenapp.application

import android.app.Application
import com.example.pendaftaranpondokpesantrenapp.repository.RegistrationRepository

class RegistrationApp : Application() {
    val database by lazy { RegistrationDatabase.getDatabase(this) }
    val repository by lazy { RegistrationRepository(database.registrationDao()) }
}