package com.example.e_commerce.presention.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.e_commerce.R

import com.example.e_commerce.databinding.FragmentDetailsCardBinding
import com.example.e_commerce.presention.payment.Constant
import com.paymob.acceptsdk.IntentConstants
import com.paymob.acceptsdk.PayActivity
import com.paymob.acceptsdk.PayActivityIntentKeys
import com.paymob.acceptsdk.PayResponseKeys
import com.paymob.acceptsdk.SaveCardResponseKeys
import com.paymob.acceptsdk.ThreeDSecureWebViewActivty
import com.paymob.acceptsdk.ToastMaker


class DetailsCardFragment : Fragment() {

    lateinit var binding: FragmentDetailsCardBinding
    private val ACCEPT_PAYMENT_REQUEST = 10

    // Replace this with your actual payment key
    val paymentKey = Constant.finalToken

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentDetailsCardBinding.inflate(inflater,container,false)
        return binding.root    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.Button1.setOnClickListener { startPayActivityNoToken(true) }
        binding.Button2.setOnClickListener { startPayActivityNoToken(false) }
        binding.Button3.setOnClickListener { startPayActivityToken() }

    }

    private fun startPayActivityNoToken(showSavedCard:Boolean)
    {
        val pay_intent = Intent(requireActivity(), PayActivity::class.java)
        putNormalExtras(pay_intent)
        pay_intent.putExtra(PayActivityIntentKeys.SAVE_CARD_DEFAULT, false)
        pay_intent.putExtra(PayActivityIntentKeys.SHOW_SAVE_CARD,showSavedCard)
        pay_intent.putExtra(PayActivityIntentKeys.THEME_COLOR, ContextCompat.getColor(requireActivity(), R.color.ThemeColor))

        pay_intent.putExtra("ActionBar", false)
        pay_intent.putExtra("language", "en")
        startActivityForResult(pay_intent,
            ACCEPT_PAYMENT_REQUEST)
        val secure_intent = Intent(requireActivity(), ThreeDSecureWebViewActivty::class.java)
        secure_intent.putExtra("ActionBar", false)
    }

    private fun startPayActivityToken()
    {
        val pay_intent = Intent(requireActivity(), PayActivity::class.java)

        putNormalExtras(pay_intent)
        // replace this with your actual card token
        // replace this with your actual card token
        pay_intent.putExtra("language", "en")
        pay_intent.putExtra(PayActivityIntentKeys.TOKEN, "6088c38c19705a495f1727561d4f4814b2ed7e45e9cd80c72f233253")
        pay_intent.putExtra(PayActivityIntentKeys.MASKED_PAN_NUMBER, "xxxx-xxxx-xxxx-1234")
        pay_intent.putExtra(PayActivityIntentKeys.SAVE_CARD_DEFAULT, false)
        pay_intent.putExtra(PayActivityIntentKeys.SHOW_SAVE_CARD, false)
        pay_intent.putExtra("ActionBar", false)
        pay_intent.putExtra(
            PayActivityIntentKeys.THEME_COLOR,
            resources.getColor(R.color.purple_500)
        )

        pay_intent.putExtra(PayActivityIntentKeys.FIRST_NAME, "Cliffo")
        pay_intent.putExtra(PayActivityIntentKeys.LAST_NAME, "Nicol")
        pay_intent.putExtra(PayActivityIntentKeys.BUILDING, "8028")
        pay_intent.putExtra(PayActivityIntentKeys.FLOOR, "42")
        pay_intent.putExtra(PayActivityIntentKeys.APARTMENT, "803")
        pay_intent.putExtra(PayActivityIntentKeys.CITY, "Jask")
        pay_intent.putExtra(PayActivityIntentKeys.STATE, "Uta")
        pay_intent.putExtra(PayActivityIntentKeys.COUNTRY, "CR")
        pay_intent.putExtra(PayActivityIntentKeys.EMAIL, "claudette09@exa.com")
        pay_intent.putExtra(PayActivityIntentKeys.PHONE_NUMBER, "+86(8)9135210487")
        pay_intent.putExtra(PayActivityIntentKeys.POSTAL_CODE, "01898")

        startActivityForResult(
            pay_intent, ACCEPT_PAYMENT_REQUEST
        )
    }

    private fun putNormalExtras(intent: Intent) {
        intent.putExtra(PayActivityIntentKeys.PAYMENT_KEY, paymentKey)
        Log.d("aaaa",paymentKey)
        intent.putExtra(PayActivityIntentKeys.THREE_D_SECURE_ACTIVITY_TITLE, "Verification")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val extras = data?.extras
        if (requestCode == ACCEPT_PAYMENT_REQUEST) {
            if (resultCode == IntentConstants.USER_CANCELED) {
                // User canceled and did no payment request was fired
                ToastMaker.displayShortToast(requireActivity(), "User canceled!!")
            } else if (resultCode == IntentConstants.MISSING_ARGUMENT) {
                // You forgot to pass an important key-value pair in the intent's extras
                ToastMaker.displayShortToast(
                    requireActivity(),
                    "Missing Argument == " + extras!!.getString(IntentConstants.MISSING_ARGUMENT_VALUE)
                )
            } else if (resultCode == IntentConstants.TRANSACTION_ERROR) {
                // An error occurred while handling an API's response
                ToastMaker.displayShortToast(
                    requireActivity(),
                    "Reason == " + extras!!.getString(IntentConstants.TRANSACTION_ERROR_REASON)
                )
            } else if (resultCode == IntentConstants.TRANSACTION_REJECTED) {
                // User attempted to pay but their transaction was rejected
                Toast.makeText(requireActivity(), "REJECTED", Toast.LENGTH_SHORT).show()

                // Use the static keys declared in PayResponseKeys to extract the fields you want
                ToastMaker.displayShortToast(requireActivity(), extras!!.getString(PayResponseKeys.DATA_MESSAGE))
                Log.d("osmaaan",extras!!.getString(PayResponseKeys.DATA_MESSAGE).toString())
            } else if (resultCode == IntentConstants.TRANSACTION_REJECTED_PARSING_ISSUE) {
                // User attempted to pay but their transaction was rejected. An error occured while reading the returned JSON
                ToastMaker.displayShortToast(
                    requireActivity(),
                    extras!!.getString(IntentConstants.RAW_PAY_RESPONSE)
                )
            } else if (resultCode == IntentConstants.TRANSACTION_SUCCESSFUL) {
                // User finished their payment successfully
                Toast.makeText(requireActivity(), "SUCCESS", Toast.LENGTH_SHORT).show()



// Remove the current fragment and navigate back to the previous fragment
                // val fragmentManager: FragmentManager = getSupportFragmentManager()
                // fragmentManager?.popBackStack()


                // Use the static keys declared in PayResponseKeys to extract the fields you want
                ToastMaker.displayShortToast(requireActivity(), extras!!.getString(PayResponseKeys.DATA_MESSAGE))
            } else if (resultCode == IntentConstants.TRANSACTION_SUCCESSFUL_PARSING_ISSUE) {
                // User finished their payment successfully. An error occured while reading the returned JSON.
                ToastMaker.displayShortToast(requireActivity(), "TRANSACTION_SUCCESSFUL - Parsing Issue")

                // ToastMaker.displayShortToast(this, extras.getString(IntentConstants.RAW_PAY_RESPONSE));
            } else if (resultCode == IntentConstants.TRANSACTION_SUCCESSFUL_CARD_SAVED) {
                // User finished their payment successfully and card was saved.
                Toast.makeText(requireActivity(), "SUCCESS CARD SAVE", Toast.LENGTH_SHORT).show()

                // Use the static keys declared in PayResponseKeys to extract the fields you want
                // Use the static keys declared in SaveCardResponseKeys to extract the fields you want
                ToastMaker.displayShortToast(
                    requireActivity(),
                    "Token == " + extras!!.getString(SaveCardResponseKeys.TOKEN)
                )
            } else if (resultCode == IntentConstants.USER_CANCELED_3D_SECURE_VERIFICATION) {
                ToastMaker.displayShortToast(requireActivity(), "User canceled 3-d scure verification!!")

                // Note that a payment process was attempted. You can extract the original returned values
                // Use the static keys declared in PayResponseKeys to extract the fields you want
                ToastMaker.displayShortToast(requireActivity(), extras!!.getString(PayResponseKeys.PENDING))
            } else if (resultCode == IntentConstants.USER_CANCELED_3D_SECURE_VERIFICATION_PARSING_ISSUE) {
                ToastMaker.displayShortToast(
                    requireActivity(),
                    "User canceled 3-d scure verification - Parsing Issue!!"
                )

                // Note that a payment process was attempted.
                // User finished their payment successfully. An error occured while reading the returned JSON.
                ToastMaker.displayShortToast(
                    requireActivity(),
                    extras!!.getString(IntentConstants.RAW_PAY_RESPONSE)
                )
            }
        }

    }

}