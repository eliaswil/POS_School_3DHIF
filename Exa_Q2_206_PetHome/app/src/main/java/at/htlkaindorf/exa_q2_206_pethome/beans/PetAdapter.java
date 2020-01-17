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

import java.nio.file.Paths;
import java.util.List;

import at.htlkaindorf.exa_q2_206_pethome.MainActivity;
import at.htlkaindorf.exa_q2_206_pethome.PetHolder;
import at.htlkaindorf.exa_q2_206_pethome.R;

public class PetAdapter extends RecyclerView.Adapter<PetHolder> {
    private List<Pet> pets;
    private List<Pet> filteredPets;

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
        Picasso.get().load(pet instanceof Cat
                ? ((Cat)pet).getPictureUri().toString()
                : Paths.get(System.getProperty("user.dir"), "app", "src", "main", "res", "drawable", "ic_dog.xml").toUri().toString())
                .into(holder.getIvPicture());
        holder.getTvBirthdate().setText(pet.getDateOfBirth().toString()); // ToDo: format Date
        if(pet instanceof Dog){
            holder.getTvSize().setText("Size: " + ((Dog)pet).getSize());
        }else{
            holder.getTvSize().setText("Color: " + ((Cat)pet).getColor().toString());
        }
    }

    @Override
    public int getItemCount() {
        return filteredPets.size();
    }
}
