package com.decagon.facilitymanagementapp_group_two.ui.others

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.decagon.facilitymanagementapp_group_two.R
import com.decagon.facilitymanagementapp_group_two.adapter.ComplaintClickListener
import com.decagon.facilitymanagementapp_group_two.adapter.DashboardComplaintAdapter
import com.decagon.facilitymanagementapp_group_two.adapter.MyRequestAdapter
import com.decagon.facilitymanagementapp_group_two.databinding.FragmentDashboardBinding
import com.decagon.facilitymanagementapp_group_two.model.repository.MyRequestRepository
import com.decagon.facilitymanagementapp_group_two.network.ApiService
import com.decagon.facilitymanagementapp_group_two.utils.*
import com.decagon.facilitymanagementapp_group_two.viewmodel.FeedsViewModel
import com.decagon.facilitymanagementapp_group_two.viewmodel.MyRequestViewModel
import com.decagon.facilitymanagementapp_group_two.viewmodel.ViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

@AndroidEntryPoint
class DashboardFragment : Fragment(), ComplaintClickListener {
    /**
     * Declaration of FragmentDashboardBinding and initialization of Dashboard Adapter
     */

    private var _binding: FragmentDashboardBinding? = null
    private val binding
        get() = _binding!!

   // private val feedsViewModel by activityViewModels<FeedsViewModel>()
    private lateinit var viewModel: MyRequestViewModel

   // var complainRecyclerAdapter = DashboardComplaintAdapter(this)
    private val adapter = MyRequestAdapter()
    private var getRequest: Job? = null

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
               activity?.finish()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val apiService = provideApiService(sharedPreferences)
        val repository = MyRequestRepository(apiService)
        viewModel = ViewModelProvider(this, ViewModelFactory(repository))
            .get(MyRequestViewModel::class.java)

        /**
         * This sets the status bar to grey for the single complaint fragment if version code greater
         * than or equal marshmallow else maintains the default status bar color
         */
        setStatusBarBaseColor(requireActivity(), requireContext())



        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        /**
         * Creates the layout manager and adapter for the recycler that shows the list of Complains
         */

        val recyclerView = binding.dashboardComplaintRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val userId = sharedPreferences.getString(USER_ID, "")
        getMyRequest(userId!!)
//        val complainRecyclerView = binding.dashboardComplaintRecyclerView
//        complainRecyclerView.adapter = complainRecyclerAdapter
//        complainRecyclerView.layoutManager = LinearLayoutManager(requireContext())
//
//        feedsViewModel.myRequest.observe(viewLifecycleOwner, Observer {
//            if (it!!.isNotEmpty()) {
//                binding.noComplainText.visibility = View.GONE
//                complainRecyclerAdapter.loadData(it)
//            }
//        })



        binding.addRequest.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_submitFragment)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * Upload profile image from shared preference
         */
        val imgUrl = sharedPreferences.getString(PROFILE_IMG_URI, null)
        imgUrl?.let {
            binding.userImage.loadImage(imgUrl)
        }
    }

    /**
     * OnclickListener function to Navigate to the single Complaint Fragment
     */
    override fun onCompalinClicked() {
        findNavController().navigate(R.id.singleComplaintFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun provideApiService(sharedPreferences: SharedPreferences): ApiService {
        val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        // Add authorization token to the header interceptor
        val headerAuthorization = Interceptor { chain ->
            val request = chain.request().newBuilder()
            sharedPreferences.getString(TOKEN_NAME, null)?.let {
                request.addHeader("Authorization", "Bearer $it")
            }
            chain.proceed(request.build())
        }

        // Creates an implementation of the ApiService
        return Retrofit.Builder()
            .client(
                OkHttpClient.Builder().addInterceptor(logging)
                    .addInterceptor(headerAuthorization).build()
            )
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    private fun getMyRequest(userId: String) {
        getRequest?.cancel()
        getRequest = lifecycleScope.launch {
            viewModel.searchMyRequest(userId).collectLatest {
                adapter.submitData(it)
            }
        }
    }
}
