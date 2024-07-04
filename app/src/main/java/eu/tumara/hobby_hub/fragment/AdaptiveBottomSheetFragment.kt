package eu.tumara.hobby_hub.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import eu.tumara.hobby_hub.R
import eu.tumara.hobby_hub.adapter.AdaptiveBottomSheetAdapter
import eu.tumara.hobby_hub.model.AdaptiveBottomSheetItem

class AdaptiveBottomSheetFragment(
    private val items: List<AdaptiveBottomSheetItem>
) : BottomSheetDialogFragment() {

    private lateinit var v: View
    private lateinit var actionsView: RecyclerView
    private lateinit var btnClose: ImageView

    private lateinit var adapter: AdaptiveBottomSheetAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        v = inflater.inflate(R.layout.fragment_adaptive_bottom_sheet, container, false)

        adapter = AdaptiveBottomSheetAdapter(v.context, this, items)
        init()

        return v
    }

    private fun init() {
        actionsView = v.findViewById(R.id.actionsView)
        btnClose = v.findViewById(R.id.btnClose)

        actionsView.adapter = adapter
        actionsView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(v.context)
        actionsView.layoutManager = layoutManager
        btnClose.setOnClickListener { dismiss() }
    }
}