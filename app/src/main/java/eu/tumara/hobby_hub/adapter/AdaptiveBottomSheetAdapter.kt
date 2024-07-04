package eu.tumara.hobby_hub.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import eu.tumara.hobby_hub.R
import eu.tumara.hobby_hub.model.AdaptiveBottomSheetItem

class AdaptiveBottomSheetAdapter(
    private val context: Context,
    private val fragment: BottomSheetDialogFragment,
    private val items: List<AdaptiveBottomSheetItem>
) : RecyclerView.Adapter<AdaptiveBottomSheetAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgIcon: ImageView = itemView.findViewById(R.id.imgIcon)
        var txtTitle: TextView = itemView.findViewById(R.id.txtTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(context)
            .inflate(R.layout.fragment_adaptive_bottom_sheet_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.imgIcon.setImageResource(item.icon)
        holder.txtTitle.text = item.title

        holder.itemView.setOnClickListener { item.action.onClick(); fragment.dismiss() }
    }
}