package com.example.lap4

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.Constraints
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.*
import okhttp3.Dispatcher


class RecycleFragment : Fragment() {
    lateinit var recycle: RecyclerView
    var communicator: Communicator? = null
    var int: Int = 1
    lateinit var floatingActionButton: FloatingActionButton
    var list = listOf<Sport>()

    lateinit var adapterr: Adapterr

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dynamic, container, false)
        recycle = view.findViewById(R.id.recycle)
        floatingActionButton = view.findViewById(R.id.floatingActionButton)
        if (savedInstanceState != null) {
            int = savedInstanceState.getInt("open")
        }

        adapterr = Adapterr(listOf<Sport>())
        val layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recycle.adapter = adapterr
        recycle.layoutManager = layoutManager
        communicator = activity as Communicator
        var retrofitSer = RetrofitSer.getInstance()
        var repossitoryData = RepossitoryData(retrofitSer)

        var job: Job? = null
        var job2: Job? = null

        class MyWork(var context: Context, var workerParams: WorkerParameters) : CoroutineWorker(context, workerParams) {


            // var  list=listOf<Sport>()

            override suspend fun doWork(): Result {
                Log.d("getDatattt"," response.body()?.sports.toString()")
                var job:Job?=null

                var retrofitSer=RetrofitSer.getInstance()

                job= CoroutineScope(Dispatchers.IO).launch {

                    val response=RepossitoryData(retrofitSer).getAllData2()

                    withContext(Dispatchers.Main){
                        Log.d("getDatattt", response.body()?.sports.toString())
                        Toast.makeText(context,"dfssd",Toast.LENGTH_LONG).show()
                       adapterr.listModel =response.body()?.sports!!
                        adapterr.notifyDataSetChanged()



                    }
                }
                return Result.success()
            }



        }


        lifecycle.coroutineScope.launch {

            job = CoroutineScope(Dispatchers.IO).launch {

                val response = RepossitoryData(retrofitSer).getAllData2()

                withContext(Dispatchers.Main) {
                    //adapterr.listModel= response.body()?.sports!!
                    list = response.body()!!.sports
                    adapterr.listModel = list
                    adapterr.notifyDataSetChanged()
                }
            }
        }






        floatingActionButton.setOnClickListener {


            val constraints = androidx.work.Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresCharging(false)
                .build()
            val myWorkRequest: WorkRequest =
                OneTimeWorkRequestBuilder<MyWork>()
                    .setConstraints(constraints)
                    .build()
            WorkManager.getInstance(activity!!).enqueue(myWorkRequest)


        }


        adapterr.setOnItemClick(object : Adapterr.OnItemClick {
            override fun onClick(position: Int) {
                // ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    val intent = Intent(context, MainActivity2::class.java)
                    intent.putExtra("position", position)
                    intent.putExtra("image2", list.get(position).strSportThumb)
                    intent.putExtra("data", list.get(position).strSportDescription)
                    intent.putExtra("from", list.get(position).strFormat)

                    startActivity(intent)
                }
                list.get(position).strSportThumb?.let {
                    list.get(position).strSportDescription?.let { it1 ->
                        list.get(position).strFormat?.let { it2 ->
                            communicator!!.setCounter(
                                it,
                                it1, it2
                            )
                        }
                    }
                }

                Toast.makeText(context, "" + position, Toast.LENGTH_LONG).show()
            }


        })


        int = 0
        return view
    }


    private fun checkForInternet(context: Context): Boolean {

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            val network = connectivityManager.activeNetwork ?: return false

            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {

                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            // if the android version is below M
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        int = 1
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("open", int)
    }

}

interface Communicator {
    fun setCounter(imag:String,data:String,fom:String)
}
