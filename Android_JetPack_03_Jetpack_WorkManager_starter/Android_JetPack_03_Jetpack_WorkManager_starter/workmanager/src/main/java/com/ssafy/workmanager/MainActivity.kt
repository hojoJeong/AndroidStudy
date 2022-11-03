package com.ssafy.workmanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.ssafy.workmanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object {
        private const val WORK_TAG = "WORK_TAG"
    }
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startSimpleWorkerBtn.setOnClickListener {
            val inputData = Data.Builder()
                .putInt(SimpleWorker.EXTRA_NUMBER, 5)
                .build()

            val simpleRequest : OneTimeWorkRequest =
                OneTimeWorkRequestBuilder<SimpleWorker>()
                    .setInputData(inputData)
                    .addTag(WORK_TAG)
                    .build()

            val simple2Request : OneTimeWorkRequest =
                OneTimeWorkRequestBuilder<Simple2Worker>()
                    .addTag(WORK_TAG)
                    .build()

            val workManager = WorkManager.getInstance(this)
            workManager
                .beginWith(simpleRequest)
                .then(simple2Request)
                .enqueue()

            val status = workManager.getWorkInfoByIdLiveData(simpleRequest.id)
            status.observe(this ){ info ->
                val workFinished = info!!.state.isFinished
                val result = info.outputData.getInt(SimpleWorker.EXTRA_RESULT, 0)
                binding.simpleWorkStatusText.text = when (info.state) {
                    WorkInfo.State.SUCCEEDED,
                    WorkInfo.State.FAILED-> {
                        "work status: ${info.state}, result: ${result}, finished: $workFinished"
                    }
                    else -> {
                        "work status: ${info.state}, finished: $workFinished"
                    }
                }
            }

            val status2 = workManager.getWorkInfoByIdLiveData(simple2Request.id)
            status2.observe(this) { info ->
                val result = info?.outputData?.getInt(SimpleWorker.EXTRA_RESULT, 0)
                binding.simpleWork2StatusText.text = when (info.state) {
                    WorkInfo.State.SUCCEEDED,
                    WorkInfo.State.FAILED-> {
                        "work status: ${info.state}, result: $result"
                    }
                    else -> {
                        "work status: ${info.state}"
                    }
                }
            }
        }

        binding.cancelSimpleWorkerBtn.setOnClickListener {
            val workManager = WorkManager.getInstance(this)
            workManager.cancelAllWorkByTag(WORK_TAG)

        }

    }
}


