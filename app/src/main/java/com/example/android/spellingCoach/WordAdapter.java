package com.example.android.spellingCoach;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by byilm on 4/13/2017.
 */

public class WordAdapter extends ArrayAdapter<SpellWord> {
    public WordAdapter(@NonNull Context context, @NonNull List<SpellWord> objects) {
        super(context, 0, objects);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View wordItemView = convertView;
        if(wordItemView == null) {
            wordItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.word_item, parent, false);
        }

        // Get the Word object located at this position in the list
        final SpellWord currentWord = getItem(position);

        TextView wordTextView = (TextView) wordItemView.findViewById(R.id.spell_word);
        wordTextView.setText(currentWord.getWord());

        //The following lines attaches an oncClickListener to the Word in the WordList Activity
        //Such that when clicked on the word it opens up a new activity displaying the information
        //about the word. Works BUT
        //!!!!!!NOT SURE IF THIS IS THE BEST WAY TO DO THIS!!!!!
        //! Additional Note: If we want to attach an onclick listener to the whole adapterView item
        //! list view has a dedicated method setItemOnClickListener. This method must be used after the
        //! attachment of adapter to the listView (that is in WordList). If we can get the id of the clicked view
        //! within the adapter item, this method can be used instead to the the same action that we are using
        //! below. It may also be more proper. If we set the wordlist such that the word info shows up when touched
        //! anywhere in the item, the above mentioned method must be used.//
        final Context parent_context=parent.getContext();
        wordTextView.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View view) {
                                              Intent i = new Intent(parent_context,WordInfo.class);
                                              i.putExtra("WORDID",currentWord.getWord_id());
                                              parent_context.startActivity(i);
                                          }
                                      });

        Button tryButton=(Button) wordItemView.findViewById(R.id.try_word);
        tryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(parent_context,TryWord.class);
                i.putExtra("WORDID",currentWord.getWord_id());
                parent_context.startActivity(i);
            }
        });
        //END of OnclickLister attachment code

        TextView meaningTextView = (TextView) wordItemView.findViewById(R.id.word_meaning);
        meaningTextView.setText(currentWord.getMeaning());

        // Find the ImageView in the list_item.xml layout with the ID list_item_icon
        //ImageView iconView = (ImageView) listItemView.findViewById(R.id.list_item_icon);
        // Get the image resource ID from the current AndroidFlavor object and
        // set the image to iconView
        //iconView.setImageResource(currentAndroidFlavor.getImageResourceId());

        return wordItemView;

    }
}
