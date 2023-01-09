package pablo.conejos.chirivella.ejer05_apidisney.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import pablo.conejos.chirivella.ejer05_apidisney.R;
import pablo.conejos.chirivella.ejer05_apidisney.VerPersoanjeActivity;
import pablo.conejos.chirivella.ejer05_apidisney.modelos.General;
import pablo.conejos.chirivella.ejer05_apidisney.modelos.Personaje;

public class PersonajesAdapter extends RecyclerView.Adapter<PersonajesAdapter.PersonajeVH> {


    private List<Personaje> objects;
    private Context context;
    private int resource;

    public PersonajesAdapter(List<Personaje> objects, Context context, int resource) {
        this.objects = objects;
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public PersonajeVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PersonajeVH(LayoutInflater.from(context).inflate(resource,null)); //no necesitamos la variable view porque ya se lo indicamos en el card view
    }

    @Override
    public void onBindViewHolder(@NonNull PersonajeVH holder, int position) {

        Personaje p = objects.get(position);

        Picasso.get()
                .load(p.getImageUrl())//URL DE LA IMAGEN
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.imgPersonaje);//IMAGEVIEW DONDE MOSTAR LA IMAGEN

        holder.lblNombre.setText(p.getName());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("ID", String.valueOf(p.getId()));
                Intent intent = new Intent(context, VerPersoanjeActivity.class);

                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public class PersonajeVH extends RecyclerView.ViewHolder {
        TextView lblNombre;
        ImageView imgPersonaje;

        public PersonajeVH(@NonNull View itemView) {
            super(itemView);

            lblNombre = itemView.findViewById(R.id.lblPersonajeVH);
            imgPersonaje = itemView.findViewById(R.id.imgPersonajeVH);

        }
    }
}
