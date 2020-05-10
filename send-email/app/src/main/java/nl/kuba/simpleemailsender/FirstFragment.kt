package nl.kuba.simpleemailsender

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.Fragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class FirstFragment : Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_ok).setOnClickListener {
            val content = view.findViewById<AppCompatEditText>(R.id.text_message).text
            sendEmail(content.toString())
        }
    }

    private fun sendEmail(content: String?) {
        Mailer.sendMail(Config.EMAIL_RECIPIENT, R.string.app_name.toString(), content.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            Toast.makeText(context, "Mail was sent", Toast.LENGTH_SHORT)
                                    .show()
                        },
                        {
                            Toast.makeText(context, "Mail was not sent: " + it.toString(), Toast.LENGTH_LONG)
                                    .show()
                        })
    }
}
