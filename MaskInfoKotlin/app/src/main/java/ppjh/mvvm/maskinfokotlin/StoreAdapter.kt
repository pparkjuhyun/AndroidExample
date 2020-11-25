package ppjh.mvvm.maskinfokotlin

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ppjh.mvvm.maskinfokotlin.model.Store
import java.util.*

class StoreViewHolder(itemView: View) : ViewHolder(itemView) {
    var tvName: TextView = itemView.findViewById(R.id.tv_name)
    var tvAddr: TextView = itemView.findViewById(R.id.tv_addr)
    var tvDistance: TextView = itemView.findViewById(R.id.tv_distance)
    var tvRemainStat: TextView = itemView.findViewById(R.id.tv_remain_stat)
    var tvCount: TextView = itemView.findViewById(R.id.tv_count)

    init {
        /**
         * init block은 생성자 호출 후 바로 실행 됨
         */
//        tvName = itemView.findViewById(R.id.tv_name)
//        tvAddr = itemView.findViewById(R.id.tv_addr)
//        tvDistance = itemView.findViewById(R.id.tv_distance)
//        tvRemainStat = itemView.findViewById(R.id.tv_remain_stat)
//        tvCount = itemView.findViewById(R.id.tv_count)
    }
}

class StoreAdapter: RecyclerView.Adapter<StoreViewHolder?>() {
    var mItems: List<Store> = ArrayList<Store>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_store, parent, false)
        return StoreViewHolder(view)
    }

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        val store: Store = mItems[position]
        holder.tvName.text = store.name
        holder.tvAddr.text = store.addr
        holder.tvDistance.text = "1km"
        var count = "100개 이상"
        var remainStat = "충분"
        var color = Color.GREEN
        when (store.remain_stat) {
            "plenty" -> {
                count = "100개 이상"
                remainStat = "충분"
                color = Color.GREEN
            }
            "some" -> {
                count = "30개 이상"
                remainStat = "여유"
                color = Color.YELLOW
            }
            "few" -> {
                count = "2개 이상"
                remainStat = "매진 임박"
                color = Color.RED
            }
            "empty" -> {
                count = "1개 이하"
                remainStat = "재고 없음"
                color = Color.GRAY
            }
        }
        holder.tvRemainStat.setText(remainStat)
        holder.tvRemainStat.setTextColor(color)
        holder.tvCount.setText(count)
        holder.tvCount.setTextColor(color)
    }

    override fun getItemCount(): Int = mItems.size

    fun updateItems(items: List<Store>) {
        mItems = items
        notifyDataSetChanged()
    }
}