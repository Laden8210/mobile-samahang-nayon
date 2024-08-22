package nasaph8210.samahangnayon.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import nasaph8210.samahangnayon.R;
import nasaph8210.samahangnayon.adapter.MessageAdapter;
import nasaph8210.samahangnayon.model.Message;


public class InboxFragment extends Fragment {

    private RecyclerView rvMessage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_inbox, container, false);
        rvMessage = view.findViewById(R.id.rv_message);
        rvMessage.setAdapter(new MessageAdapter(getContext(), getMessage()));
        rvMessage.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    private List<Message> getMessage(){
        List<Message> messages = new ArrayList<>();
        messages.add(new Message("Hello", true));
        messages.add(new Message("How are you?", true));
        messages.add(new Message("I'm fine, thank you.", false));
        messages.add(new Message("What about you?", false));
        messages.add(new Message("I'm doing well.", true));
        messages.add(new Message("Good to hear!", true));
        messages.add(new Message("What are you up to today?", true));
        messages.add(new Message("Just working on some projects.", false));
        messages.add(new Message("That sounds interesting!", true));
        messages.add(new Message("Yes, it keeps me busy.", false));
        messages.add(new Message("Any plans for the weekend?", true));
        messages.add(new Message("Not yet, maybe just relax.", false));
        messages.add(new Message("Sounds like a good plan.", true));
        messages.add(new Message("Indeed. How about you?", false));
        messages.add(new Message("I might go hiking.", true));
        messages.add(new Message("That sounds fun!", false));
        messages.add(new Message("Yes, I'm looking forward to it.", true));
        messages.add(new Message("Enjoy your hike!", false));
        messages.add(new Message("Thank you! Have a great weekend!", true));
        messages.add(new Message("You too!", false));

        return messages;
    }
}