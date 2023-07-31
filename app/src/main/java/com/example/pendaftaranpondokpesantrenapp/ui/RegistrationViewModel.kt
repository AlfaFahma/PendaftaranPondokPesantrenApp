package com.example.pendaftaranpondokpesantrenapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.pendaftaranpondokpesantrenapp.model.Registration
import com.example.pendaftaranpondokpesantrenapp.repository.RegistrationRepository
import kotlinx.coroutines.launch

class RegistrationViewModel (private val repository: RegistrationRepository): ViewModel() {
    val allRegistrations: LiveData<List<Registration>> = repository.allRegistrations.asLiveData()

    fun insert(registration: Registration) = viewModelScope.launch {
        repository.insertRegistration(registration)
    }

    fun delete(registration: Registration) = viewModelScope.launch {
        repository.deleteRegistration(registration)
    }

    fun update(registration: Registration) = viewModelScope.launch {
        repository.updateRegistration(registration)
    }
}

class RegistrationViewModelFactory(private val repository: RegistrationRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom((RegistrationViewModel::class.java))){
            return RegistrationViewModel(repository) as T
        }
        throw IllegalAccessException("Unknown ViewModel class")
        return super.create(modelClass)
    }
}