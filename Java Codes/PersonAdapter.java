package com.example.myfirstfinalapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PersonAdapter extends  RecyclerView.Adapter<PersonAdapter.ViewHolder>{
  private ArrayList<Person> personList;
  LayoutInflater inflater;

  public PersonAdapter(Context context, ArrayList<Person> personList) {
    this.personList = personList;
    inflater = LayoutInflater.from(context);
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
    ViewHolder holder = new ViewHolder(view);
    return holder;
  }

  public void onBindViewHolder(ViewHolder holder, int position) {
    Person selectedPerson = personList.get(position);
    holder.setData(selectedPerson, position);
  }

  @Override
  public int getItemCount() {
    return personList.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    ImageView image;
    TextView username, password;
    RelativeLayout parentLayout;
    Button psButton;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      image = itemView.findViewById(R.id.avatar);
      username = itemView.findViewById(R.id.username);
      password = itemView.findViewById(R.id.password);
      psButton = itemView.findViewById(R.id.pwButton);
      parentLayout = itemView.findViewById(R.id.parentLayout);
      psButton.setOnClickListener(this);
    }

    public void setData(Person selectedPerson, int position) {
      this.image.setImageResource(selectedPerson.getImageName());
      this.password.setText(selectedPerson.getPassword());
      this.username.setText(selectedPerson.getUsername());
    }

    @Override
    public void onClick(View view) {
      if(password.getVisibility() == View.INVISIBLE)
        password.setVisibility(View.VISIBLE);
      else
        password.setVisibility(View.INVISIBLE);
    }
  }

}