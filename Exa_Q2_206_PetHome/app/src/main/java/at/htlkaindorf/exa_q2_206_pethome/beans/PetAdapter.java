package at.htlkaindorf.exa_q2_206_pethome.beans;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.Comparator;
import java.util.List;

import at.htlkaindorf.exa_q2_206_pethome.PetHolder;
import at.htlkaindorf.exa_q2_206_pethome.R;

public class PetAdapter extends RecyclerView.Adapter<PetHolder> {
    private List<Pet> filteredPets;

    public void init(List<Pet> pets){
        filteredPets = pets;
    }


    @NonNull
    @Override
    public PetHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pet_item, parent, false);
        RelativeLayout relativeLayout = view.findViewById(R.id.rlItem);
        ImageView ivPicture = view.findViewById(R.id.ivPicture);
        TextView tvName = view.findViewById(R.id.tvName);
        ImageView ivGender = view.findViewById(R.id.ivGender);
        TextView tvBirthdate = view.findViewById(R.id.tvBirthdate);
        TextView tvSize = view.findViewById(R.id.tvSize);
        return new PetHolder(view, relativeLayout, ivPicture, tvName, ivGender, tvBirthdate, tvSize);
    }

    @Override
    public void onBindViewHolder(@NonNull PetHolder holder, int position) {
        Pet pet = filteredPets.get(position);
        holder.getIvGender().setImageResource(pet.getGender().equals(Gender.FEMALE) ? R.drawable.ic_female : R.drawable.ic_male);
        holder.getTvBirthdate().setText(pet.getDateOfBirth().toString());
        if(pet instanceof Dog){
            holder.getTvSize().setText("Size: " + ((Dog)pet).getSize());
            holder.getIvPicture().setImageResource(R.drawable.ic_dog);
        }else{
            holder.getTvSize().setText("Color: " + ((Cat)pet).getColor().toString());
            Picasso.get().load(((Cat)pet).getPictureUri()).into(holder.getIvPicture());
        }
        holder.getTvName().setText(pet.getName());
    }

    @Override
    public int getItemCount() {
        return filteredPets.size();
    }
}
