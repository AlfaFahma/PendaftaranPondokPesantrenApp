package com.example.pendaftaranpondokpesantrenapp.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.pendaftaranpondokpesantrenapp.R
import com.example.pendaftaranpondokpesantrenapp.application.RegistrationApp
import com.example.pendaftaranpondokpesantrenapp.databinding.FragmentSecondBinding
import com.example.pendaftaranpondokpesantrenapp.model.Registration
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerDragListener{

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private lateinit var applicationContext: Context
    private val registrationViewModel: RegistrationViewModel by viewModels {
        RegistrationViewModelFactory((applicationContext as RegistrationApp).repository)
    }
    private val args : SecondFragmentArgs by navArgs()
    private var registration : Registration? = null
    private lateinit var mMap : GoogleMap
    private var currentLatLang: LatLng?= null
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val cameraRequestCode = 2

    override fun onAttach(context: Context) {
        super.onAttach(context)
        applicationContext = requireContext().applicationContext
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registration = args.registration
        if (registration != null){
            binding.deleteButton.visibility = View.VISIBLE
            binding.saveButton.text = "Ubah"
            binding.nameEditText.setText(registration?.name)
            binding.addressEditText.setText(registration?.address)
            binding.phoneEditText.setText(registration?.phone)
            binding.parentEditText.setText(registration?.parent)
        }
        //binding google map
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        checkPermission()

        val name = binding.nameEditText.text
        val address = binding.addressEditText.text
        val phone = binding.phoneEditText.text
        val parent = binding.parentEditText.text
        binding.saveButton.setOnClickListener {
            //kita kasih kondisi jika nama, alamat, no tlp dan wali kosong tidak bisa menyimpan
            if (name.isEmpty()) {
                Toast.makeText(context,"Nama tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else if (address.isEmpty()) {
                Toast.makeText(context, "Alamat tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else if (phone.isEmpty()) {
                    Toast.makeText(context, "No. Telpon tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else if (parent.isEmpty()) {
                Toast.makeText(context, "Nama Wali tidak boleh kosong", Toast.LENGTH_SHORT).show()

            }else {
                if (registration == null) {
                    val registration = Registration(
                        0, name.toString(), address.toString(),
                        phone.toString(), parent.toString(), currentLatLang?.latitude, currentLatLang?.longitude
                    )
                    registrationViewModel.insert(registration)
                } else {
                    val registration = Registration(
                        0, name.toString(), address.toString(),
                        phone.toString(), parent.toString(), currentLatLang?.latitude,  currentLatLang?.longitude
                    )
                    registrationViewModel.update(registration)
                }

                findNavController().popBackStack()//untuk dismiss halaman ini

            }
        }

        binding.deleteButton.setOnClickListener {
            registration?.let { registrationViewModel.delete(it) }
            findNavController().popBackStack()
        }

        binding.cameraButton.setOnClickListener {
            checkCameraPermission()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val uiSettings = mMap.uiSettings
        uiSettings.isZoomControlsEnabled = true
        mMap.setOnMarkerDragListener(this)
    }

    override fun onMarkerDrag(p0: Marker) {
        TODO("Not yet implemented")
    }

    override fun onMarkerDragEnd(marker: Marker) {
        val newPosition = marker.position
        currentLatLang = LatLng(newPosition.latitude, newPosition.longitude)
        Toast.makeText(context, currentLatLang.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun onMarkerDragStart(p0: Marker) {
    }

    private fun checkPermission() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(applicationContext)
        if (ContextCompat.checkSelfPermission(
            applicationContext,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        ) {
            getCurrentLocation()
        }else{
            Toast.makeText(applicationContext, "Akses Lokasi Di Tolak", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getCurrentLocation() {
        // ngecek jika permission tidak di setujui maka akan berhenri di kondisi if
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) !== PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        fusedLocationProviderClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null){
                    var latLng = LatLng(location.latitude, location.longitude)
                    currentLatLang = latLng
                    var title = "Marker"

                    if (registration !=null) {
                        title = registration?.name.toString()
                        val newCurrentLocation = LatLng(registration?.latitude!!, registration?.longitude!!)
                        latLng = newCurrentLocation
                    }
                    val markerOptions = MarkerOptions()
                        .position(latLng)
                        .title(title)
                        .draggable(true)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_registration_location))
                    mMap.addMarker(markerOptions)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))


                }
            }
    }
    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                android.Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED){
            activity?.let {
                ActivityCompat.requestPermissions(
                    it,
                    arrayOf(android.Manifest.permission.CAMERA),
                    cameraRequestCode
                )
            }
        }else{
           val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, cameraRequestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == cameraRequestCode) {
            val photo: Bitmap = data?.extras?.get("data") as Bitmap
            binding.photoImageView.setImageBitmap(photo)
        }
    }
}