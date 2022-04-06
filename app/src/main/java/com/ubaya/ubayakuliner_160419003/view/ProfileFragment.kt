package com.ubaya.ubayakuliner_160419003.view

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ubaya.ubayakuliner_160419003.R
import com.ubaya.ubayakuliner_160419003.util.arrGender
import com.ubaya.ubayakuliner_160419003.util.loadImage
import com.ubaya.ubayakuliner_160419003.util.userId
import com.ubaya.ubayakuliner_160419003.viewmodel.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_detail_restaurant.*
import kotlinx.android.synthetic.main.fragment_profile.*
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    private lateinit var viewModel: ProfileViewModel
    private val myCalendar: Calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = ArrayAdapter(view.context, R.layout.myspinner_layout, arrGender)
        adapter.setDropDownViewResource(R.layout.myspinner_item_layout)
        spinnerGender.adapter = adapter

        val date =
            OnDateSetListener { _, year, month, day ->
                myCalendar[Calendar.YEAR] = year
                myCalendar[Calendar.MONTH] = month
                myCalendar[Calendar.DAY_OF_MONTH] = day
                updateLabel()
            }
        textInputBOD.setOnClickListener {
            DatePickerDialog(
                view.context,
                date,
                myCalendar[Calendar.YEAR],
                myCalendar[Calendar.MONTH],
                myCalendar[Calendar.DAY_OF_MONTH]
            ).show()
        }

        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        viewModel.fetch(userId.toString())

        observeViewModel(adapter)
    }

    private fun updateLabel() {
        val myFormat = "dd-MM-yyyy"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        textInputBOD.setText(dateFormat.format(myCalendar.time))
    }

    private fun observeViewModel(adapter:ArrayAdapter<String>){
        viewModel.profileLiveData.observe(viewLifecycleOwner) {
            textUsername.text = "Username : ${it.username}"
            textInputName.setText(it.name)
            spinnerGender.setSelection(adapter.getPosition(it.gender))

            //Birth of Date
            val bod:List<String> = it.birthDate.split("-")
            myCalendar[Calendar.YEAR] = Integer.parseInt(bod[2])
            myCalendar[Calendar.MONTH] = Integer.parseInt(bod[1])
            myCalendar[Calendar.DAY_OF_MONTH] = Integer.parseInt(bod[0])
            updateLabel()
            //

            textInputPhone.setText(it.phoneNumber)
            textInputEmail.setText(it.email)
            textInputPassword.setText(it.password)
            imageProfile.loadImage(it.photoURL,progressBarProfilePhoto)
        }

        viewModel.profileLoadErrorLiveData.observe(viewLifecycleOwner) {
            textErrorProfile.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.profileLoadingLiveData.observe(viewLifecycleOwner) {
            if (it){
                scrollViewProfile.visibility = View.GONE
                progressLoadProfile.visibility = View.VISIBLE
            }else{
                scrollViewProfile.visibility = View.VISIBLE
                progressLoadProfile.visibility = View.GONE
            }
        }
    }
}