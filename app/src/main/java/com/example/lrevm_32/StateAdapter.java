package com.example.lrevm_32;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StateAdapter extends RecyclerView.Adapter<StateAdapter.ViewHolder>{

    private final LayoutInflater inflater;
    private static List<States> states;
    private Boolean  haveDelete = false;
    private Boolean haveEdit = false;




    StateAdapter(Context context, List<States> states) {
        this.states = states;
        this.inflater = LayoutInflater.from(context);

    }
    @Override
    public StateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view, parent.getContext());
/*
        return new ViewHolder(view, parent.getContext());
*/

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(StateAdapter.ViewHolder holder, int position) {
        States state = states.get(position);
        holder.timeTextView.setText(state.getTime());
        holder.noteTextView.setText(state.getNote());

        if (state.getTime() == "")
        {
            holder.timeTextView.setText("");
            holder.noteTextView.setText("     На этот день нет планов");
            holder.delete.setVisibility(View.INVISIBLE);
            holder.delete.setVisibility(View.INVISIBLE);
            holder.edit.setVisibility(View.INVISIBLE);
            holder.textView14.setVisibility(View.INVISIBLE);
            holder.textView16.setVisibility(View.INVISIBLE);
            holder.constraintLayout2.setBackgroundResource(R.drawable.null_custom);
            holder.delete.setClickable(false);
            holder.edit.setClickable(false);
            holder.delete.setClickable(false);
        }

    }

    @Override
    public int getItemCount() {
        return states.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView noteTextView;
        final TextView timeTextView;
        final RecyclerView list;
        final TextView delete;
        final TextView edit;
        final TextView textView14;
        final TextView textView16;
        final View constraintLayout2;

/*
        ViewHolder(View view, Context context){
*/

        ViewHolder(View view, Context context){
            super(view);
            noteTextView = view.findViewById(R.id.noteTextView);
            timeTextView = view.findViewById(R.id.timeTextView);
            list = view.findViewById(R.id.list);
            delete = view.findViewById(R.id.delete);
            edit = view.findViewById(R.id.edit);
            textView14 = view.findViewById(R.id.textView14);
            textView16 = view.findViewById(R.id.textView16);
            constraintLayout2 = view.findViewById(R.id.constraintLayout2);


            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int positionIndex = getAdapterPosition();
                    AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle("Удаление записи").setMessage("Вы точно хотите удалить запись?")
                            .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                                @SuppressLint("ResourceAsColor")
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    States state = states.get(positionIndex);
                                    states.remove(positionIndex);
                                    if (states.size() == 0)
                                    {
                                        timeTextView.setText("");
                                        noteTextView.setText("     На этот день нет планов");
                                        delete.setVisibility(View.INVISIBLE);
                                        edit.setVisibility(View.INVISIBLE);
                                        textView14.setVisibility(View.INVISIBLE);
                                        textView16.setVisibility(View.INVISIBLE);
                                        constraintLayout2.setBackgroundResource(R.drawable.null_custom);                                        delete.setClickable(false);
                                        edit.setClickable(false);
                                    }
                                    sendStates(state.getFullDate(), context);
                                    notifyItemRemoved(positionIndex);
                                    //notifyDataSetChanged();
                                    sendEditNote("", context, "");
                                }
                            })
                            .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    builder.show();

                }


            });
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Редактирование записи");


                    // Set up the input
                    final EditText input = new EditText(context);
                    input.setHint("     Введите изменённую запись");
                    input.setHeight(22);
                    // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    builder.setView(input);
                    // Set up the buttons
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int positionIndex = getAdapterPosition();
                            States state = states.get(positionIndex);
                            String m_Text = input.getText().toString();
                            noteTextView.setText(m_Text);
                            state.setNote(m_Text);
                            sendEditNote(state.getFullDate(), context, m_Text);
                            sendStates("", context);
                        }
                    });
                    builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();
                }
            });


            /*SharedPreferences sharedPref = context.getSharedPreferences("my_prefs", context.MODE_PRIVATE);
            int bg = sharedPref.getInt("background_resource_solid", android.R.color.white); // the second parameter will be fallback if the preference is not found
            list.setBackgroundResource(bg);*/



        }

    }


    private void sendStates(String fullName, Context context)
    {
        //отправка нового фона в другие активити
        SharedPreferences sharedPref = context.getSharedPreferences("my_prefs", context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPref.edit();
        editor2.putString("statesRemoveIndex", fullName);

        editor2.apply();
    }

    private void sendEditNote(String fullName, Context context, String edit)
    {
        //отправка нового фона в другие активити
        SharedPreferences sharedPref = context.getSharedPreferences("my_prefs", context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPref.edit();
        editor2.putString("EditNoteKey", fullName);
        editor2.putString("EditNoteText", edit);

        editor2.apply();
    }


}
