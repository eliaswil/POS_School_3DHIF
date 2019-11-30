package at.htlkaindorf.exa_q2_201_zodiacsign.bl;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import at.htlkaindorf.exa_q2_201_zodiacsign.R;
import at.htlkaindorf.exa_q2_201_zodiacsign.ZodiacViewHolder;

public class ZodiacSignAdapter extends RecyclerView.Adapter<ZodiacViewHolder>{
    private List<ZodiacSign> zodiacSigns = Arrays.asList(
            new ZodiacSign("Wassermann", MonthDay.of(1, 21), R.drawable.aquarius),
            new ZodiacSign("Fische", MonthDay.of(2, 20), R.drawable.pisces),
            new ZodiacSign("Widder", MonthDay.of(3, 21), R.drawable.aries),
            new ZodiacSign("Stier", MonthDay.of(4, 21), R.drawable.taurus),
            new ZodiacSign("Zwillinge", MonthDay.of(5, 21), R.drawable.gemini),
            new ZodiacSign("Krebs", MonthDay.of(6, 22), R.drawable.cancer),
            new ZodiacSign("Löwe", MonthDay.of(7, 23), R.drawable.leo),
            new ZodiacSign("Jungfrau", MonthDay.of(8, 24), R.drawable.virgo),
            new ZodiacSign("Waage", MonthDay.of(9, 24), R.drawable.libra),
            new ZodiacSign("Skorpion", MonthDay.of(10, 24), R.drawable.scorpius),
            new ZodiacSign("Schütze", MonthDay.of(11, 23), R.drawable.sagittarius),
            new ZodiacSign("Steinbock", MonthDay.of(12, 22), R.drawable.capricornus)
    );
    private final DateTimeFormatter DDF = DateTimeFormatter.ofPattern("dd. MMMM");


    @NonNull
    @Override
    public ZodiacViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.zodiac_item, parent, false);
        ImageView ivSymbol = view.findViewById(R.id.ivSymbol);
        TextView tvH1 = view.findViewById(R.id.tvH1);
        TextView tvDescription = view.findViewById(R.id.tvDescription);


        ZodiacViewHolder zodiacViewHolder = new ZodiacViewHolder(view, ivSymbol, tvH1, tvDescription, (RelativeLayout) view);
        return zodiacViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ZodiacViewHolder holder, int position) {

        ZodiacSign zodiacSign = zodiacSigns.get(position);
        holder.getIvSymbol().setImageResource(zodiacSign.getIdFromDrawable());
        holder.getTvH1().setText(zodiacSign.getName());
        MonthDay next = zodiacSigns.get((position + 1) % zodiacSigns.size()).getStartDate();
        String text = DDF.format(zodiacSign.getStartDate()) + " bis " + DDF.format(MonthDay.of(next.getMonth(), next.getDayOfMonth() - 1));
        holder.getTvDescription().setText(text);
    }

    @Override
    public int getItemCount() {
        return zodiacSigns.size();
    }
}
