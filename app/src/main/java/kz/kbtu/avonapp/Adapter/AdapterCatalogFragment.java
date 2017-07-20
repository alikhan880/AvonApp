package kz.kbtu.avonapp.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import kz.kbtu.avonapp.R;

/**
 * Created by abakh on 19-Jul-17.
 */

public class AdapterCatalogFragment extends RecyclerView.Adapter<AdapterCatalogFragment.ViewHolder> {
    public interface CatalogFragmentListener{

        void itemClicked(int position);
    }

    private List<String> productList;
    private CatalogFragmentListener listener;

    public AdapterCatalogFragment(List<String> productList, CatalogFragmentListener listener) {
        this.productList = productList;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_catalog, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvItemCatalog.setText(productList.get(position));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemCatalog;
        public ViewHolder(View itemView) {
            super(itemView);
            tvItemCatalog = itemView.findViewById(R.id.tv_item_catalog);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.itemClicked(getAdapterPosition());
                }
            });
        }
    }
}
