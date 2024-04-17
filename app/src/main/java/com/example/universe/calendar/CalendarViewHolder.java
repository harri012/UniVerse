//Code Adapted from: https://github.com/codeWithCal/CalendarTutorialAndroidStudio

package com.example.universe.calendar;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.universe.R;

public class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public final TextView dayOfMonth;
    private final CalendarAdapter.OnItemListener onItemListener;
    private boolean isSelected = false;


    public CalendarViewHolder(@NonNull View itemView, CalendarAdapter.OnItemListener onItemListener)
    {
        super(itemView);
        dayOfMonth = itemView.findViewById(R.id.dayTile_text);
        this.onItemListener = onItemListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        int position = getAdapterPosition();
        String dayText = (String) dayOfMonth.getText();

        // Toggle selection state
        isSelected = !isSelected;

        // Update background color based on selection state
        if (isSelected) {
            itemView.setBackgroundResource(R.drawable.selected_day_background);
        } else {
            itemView.setBackgroundResource(0); // Remove background
        }

        // Pass the selected day text and position to the listener
        onItemListener.onItemClick(position, dayText);
    
    }
}
