package com.dong.personalhealthmonitor.java_classes.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dong.personalhealthmonitor.R;
import com.dong.personalhealthmonitor.activities.MainActivity;
import com.dong.personalhealthmonitor.java_classes.SQLite.ReportDB;
import com.dong.personalhealthmonitor.java_classes.generic.Report;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

public class CardsAdapter extends ArrayAdapter<Report> {
    public CardsAdapter(Context context) {
        super(context, R.layout.layout_card);
    }


    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.layout_card, parent, false);
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Report report = getItem(position);

        holder.id.setText(report.getID());
        holder.height.setText(report.getHeight());
        holder.weight.setText(report.getWeight());
        holder.temp.setText(report.getTemp());
        holder.blood.setText(report.getBlood());
        holder.notes.setText(report.getNotes());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialAlertDialogBuilder(getContext())
                        .setTitle("Delete")
                        .setMessage("Are you sure to delete card " + report.getID() + "?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final SwipeRefreshLayout refresh = holder.v.getRootView().findViewById(R.id.refresh);
                                refresh.setRefreshing(true);
                                ReportDB reportDB = new ReportDB(getContext());
                                reportDB.report = report;
                                reportDB.db.open();
                                reportDB.delete_saved_report();
                                reportDB.db.close();
                                remove(getItem(position));
                                notifyDataSetChanged();
                                refresh.setRefreshing(false);
                                Snackbar.make(holder.v, "Deleted!", Snackbar.LENGTH_SHORT)
                                        .show();

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }
        });
        holder.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SwipeRefreshLayout refresh = holder.v.getRootView().findViewById(R.id.refresh);
                refresh.setRefreshing(true);
                ReportDB reportDB = new ReportDB(getContext());
                reportDB.report = report;
                reportDB.db.open();
                reportDB.update_saved_report();
                reportDB.db.close();
                refresh.setRefreshing(false);
                Snackbar.make(holder.v, "Saved!", Snackbar.LENGTH_SHORT)
                        .show();
            }
        });

        holder.height_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout ll = new LinearLayout(getContext());
                ll.setGravity(Gravity.CENTER);

                final TextInputEditText et = new TextInputEditText(getContext());
                et.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                et.setText(String.valueOf(report.height));
                et.setFilters(new InputFilter[]{new MainActivity.DecimalDigitsInputFilter(4, 2)});

                ll.addView(et);
                new MaterialAlertDialogBuilder(getContext())
                        .setView(ll)
                        .setTitle("Edit Height")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String text = et.getText().toString();

                                report.height = !text.equals("") ? Double.parseDouble(text) : 0.0;
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();

            }
        });
        holder.weight_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout ll = new LinearLayout(getContext());
                ll.setGravity(Gravity.CENTER);

                final TextInputEditText et = new TextInputEditText(getContext());
                et.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                et.setText(String.valueOf(report.weight));
                et.setFilters(new InputFilter[]{new MainActivity.DecimalDigitsInputFilter(4, 2)});
                ll.addView(et);
                new MaterialAlertDialogBuilder(getContext())
                        .setView(ll)
                        .setTitle("Edit Weight")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String text = et.getText().toString();
                                report.weight = !text.equals("") ? Double.parseDouble(text) : 0.0;


                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();

            }
        });
        holder.temp_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout ll = new LinearLayout(getContext());
                ll.setGravity(Gravity.CENTER);

                final TextInputEditText et = new TextInputEditText(getContext());
                et.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                et.setText(String.valueOf(report.temperature));
                et.setFilters(new InputFilter[]{new MainActivity.DecimalDigitsInputFilter(3, 1)});

                ll.addView(et);
                new MaterialAlertDialogBuilder(getContext())
                        .setView(ll)
                        .setTitle("Edit Temperature")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String text = et.getText().toString();
                                report.temperature = !text.equals("") ? Double.parseDouble(text) : 0.0;


                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();

            }
        });
        holder.blood_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout ll = new LinearLayout(getContext());

                MaterialTextView blood_div = new MaterialTextView(getContext());

                final TextInputEditText et_blood1 = new TextInputEditText(getContext());
                final TextInputEditText et_blood2 = new TextInputEditText(getContext());

                blood_div.setText("/");

                et_blood1.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                et_blood2.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                et_blood1.setText(String.valueOf(report.blood1));
                et_blood2.setText(String.valueOf(report.blood2));

                et_blood1.setFilters(new InputFilter[]{new MainActivity.DecimalDigitsInputFilter(4, 1)});
                et_blood2.setFilters(new InputFilter[]{new MainActivity.DecimalDigitsInputFilter(4, 1)});

                ll.setGravity(Gravity.CENTER);
                ll.addView(et_blood1);
                ll.addView(blood_div);
                ll.addView(et_blood2);

                new MaterialAlertDialogBuilder(getContext())
                        .setView(ll)
                        .setTitle("Edit Blood Pressure")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String t_blood1 = et_blood1.getText().toString();
                                String t_blood2 = et_blood2.getText().toString();

                                if (!t_blood1.equals("") && !t_blood2.equals("")) {
                                    report.blood1 = Double.parseDouble(t_blood1);
                                    report.blood2 = Double.parseDouble(t_blood2);
                                    if (report.blood1 == 0.0 || report.blood2 == 0.0){
                                        report.blood1 = report.blood2 = 0.0;
                                    }
                                } else{
                                    report.blood1 = 0.0;
                                    report.blood2 = 0.0;
                                }

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();

            }
        });
        holder.notes_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout ll = new LinearLayout(getContext());
                ll.setGravity(Gravity.CENTER);

                final TextInputEditText et = new TextInputEditText(getContext());
                et.setText(report.notes);

                ll.addView(et);
                new MaterialAlertDialogBuilder(getContext())
                        .setView(ll)
                        .setTitle("Edit notes")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                report.notes = et.getText().toString();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }
        });

        return convertView;
    }

    static class ViewHolder {
        MaterialCardView height_card, weight_card, temp_card, blood_card, notes_card;
        MaterialTextView id, notes, height, weight, temp, blood;
        MaterialButton save, delete;
        View v;

        ViewHolder(View view) {
            v = view;
            id = view.findViewById(R.id._id);
            height = view.findViewById(R.id.height);
            weight = view.findViewById(R.id.weight);
            temp = view.findViewById(R.id.temp);
            blood = view.findViewById(R.id.blood);
            notes = view.findViewById(R.id.notes);
            save = view.findViewById(R.id.save);
            delete = view.findViewById(R.id.delete);
            height_card = view.findViewById(R.id.height_card);
            weight_card = view.findViewById(R.id.weight_card);
            temp_card = view.findViewById(R.id.temp_card);
            blood_card = view.findViewById(R.id.blood_card);
            notes_card = view.findViewById(R.id.notes_card);
        }
    }
}