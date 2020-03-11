package apps.esampaio.com.matosecodrinks

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private val RANGE = 20;
    private val DELAY = 25L;

    class doAsync(val handler: () -> Unit) : AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg params: Void?): Void? {
            handler()
            return null
        }
    }

    lateinit var contextInstance:Context;
    lateinit var allChallenges : Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        contextInstance = this;
        allChallenges = resources.getStringArray(R.array.challenges)
        newChallengeButton.setOnClickListener {
            newChallenge()
        }
    }

    fun newChallenge() {
        newChallengeButton.isEnabled = false;
        doAsync {

            val targetIndex = Random().nextInt(allChallenges.size)
            var currentIndex = targetIndex - RANGE;
            if(currentIndex < 0){
                currentIndex += allChallenges.size;
            }

            for(i in 0..RANGE){

                runOnUiThread {
                    currentIndex++;
                    if(currentIndex >= allChallenges.size){
                        currentIndex = 0;
                    }
                    challenge_text.text = allChallenges.get(currentIndex)
                }

                Thread.sleep(DELAY)
            }
            runOnUiThread {
                newChallengeButton.isEnabled = true
            }

        }.execute();
    }

}
