package com.kelompoksatuandsatu.preducation.presentation.feature.profile

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kelompoksatuandsatu.preducation.R

class TransactionActivity : AppCompatActivity() {

    private lateinit var rvTransactionsPaid: RecyclerView
    private lateinit var rvTransactionsWaiting: RecyclerView

    private lateinit var backButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)

        // Find views by ID
        val clHeader = findViewById<ConstraintLayout>(R.id.cl_header)
        rvTransactionsPaid = findViewById(R.id.rv_transactions_paid)
        rvTransactionsWaiting = findViewById(R.id.rv_transactions_waiting)

        backButton = findViewById(R.id.iv_back)

        // Set Click Listeners
        backButton.setOnClickListener {
            // Handle back button click (e.g., navigate back to the previous screen)
            onBackPressed()
        }

        // Set up RecyclerViews
        setUpRecyclerView(rvTransactionsPaid, MockData.getMockPaidTransactions())
        setUpRecyclerView(rvTransactionsWaiting, MockData.getMockWaitingTransactions())

        // Add your logic or event listeners here
    }

    private fun setUpRecyclerView(recyclerView: RecyclerView, transactions: List<PopularCourseItem>) {
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = TransactionAdapter(transactions)
        recyclerView.adapter = adapter
        // Add any other configuration here
    }
}
