package com.example.acmeonlinestore.ui.main

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.ImageView
import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.acmeonlinestore.BR
import com.example.acmeonlinestore.R
import com.example.acmeonlinestore.databinding.ViewProductDetailsBinding
import com.example.acmeonlinestore.model.Product
import com.example.acmeonlinestore.ui.RecyclerViewAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainFragment() : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var productsAdapter: ProductsRecyclerViewAdapter
    private lateinit var viewModel: MainViewModel

    private lateinit var recyclerViewProducts: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        configRecyclerView()
        configSearch()

    }

    private fun configSearch() {
        // Disable focus on search which pops up keyboard
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        view?.findViewById<EditText>(R.id.editTextSearch)?.addTextChangedListener { editable ->
            viewModel.filterProduct(editable.toString())
        }
    }

    private fun configRecyclerView() {
        // Products adapter
        productsAdapter = ProductsRecyclerViewAdapter(viewLifecycleOwner, BR.product) {
            showDetails(it)
        }

        // Configure the recyclerView
        view?.findViewById<RecyclerView>(R.id.recyclerViewProducts)?.let { recyclerView ->

            this@MainFragment.recyclerViewProducts = recyclerView

            with(recyclerView) {
                adapter = productsAdapter
                addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            }

        }

        // Monitor for changes in products
        lifecycleScope.launch {
            viewModel.products.collect { list ->
                productsAdapter.submitList(list)
            }
        }
    }

    /**
     * Create a quick Alert showing the details of the product
     */
    private fun showDetails(product: Product) {

        val binding = DataBindingUtil.inflate<ViewProductDetailsBinding>(layoutInflater, R.layout.view_product_details, null, false)
        binding.product = product

        val dialog = AlertDialog.Builder(context).apply {
            setView(binding.root)
        }.create()

        binding.btnClose.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.sort, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btnSort -> {
                showSortAlert()
            }
        }
        return true
    }

    /**
     * Present the user with an alert to sort via price or name
     */
    private fun showSortAlert() {
        AlertDialog.Builder(context).apply {
            setTitle(R.string.sort)
            val items = arrayOf(getString(R.string.by_name), getString(R.string.by_price))
            setSingleChoiceItems(items, viewModel.currentSortRule.value) { dialog, code ->

                viewModel.currentSortRule.value = code


                // For the recycler view to go to the top of the list
                recyclerViewProducts.layoutManager?.scrollToPosition(0)

                dialog.dismiss()

            }
        }.create().show()
    }
}


class ProductsRecyclerViewAdapter(lifecycleOwner: LifecycleOwner, dataBindingID: Int, onItemClick : (Product) -> Unit ) :
    RecyclerViewAdapter<Product>(lifecycleOwner, dataBindingID, onItemClicked = onItemClick) {

    override fun getItemViewType(position: Int): Int {
        return R.layout.view_line_item // Hardcoded to only kind of view type
    }
}

@BindingAdapter("thumb")
fun ImageView.setThumb(url: String) {
    Glide.with(this).load(url).into(this);
}