package ke.co.stashare.syncsample.navigator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import ke.co.stashare.syncsample.R;

/**
 * Created by Ken Wainaina on 12/08/2017.
 */

public class PageFragment extends Fragment {

    public static PageFragment newInstance(Contact singleContact) {

        PageFragment pageFragment = new PageFragment();
        Bundle bundle = new Bundle();
      /*  bundle.putString("name", singleContact.getName());
        bundle.putString("phone", singleContact.getPhone());
        pageFragment.setArguments(bundle);*/

        bundle.putSerializable("contact", singleContact);
        pageFragment.setArguments(bundle);

        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_fragment, container, false);
        final TextView textView1 = (TextView) view.findViewById(R.id.textView1);
        final TextView textView2 = (TextView) view.findViewById(R.id.textView2);

        Contact cont= (Contact) getArguments().getSerializable("contact");

        assert cont != null;
        textView1.setText(cont.getName());
        textView2.setText(cont.getPhone());

       /* Button btnClick=(Button) view.findViewById(R.id.btnClick);

        btnClick.setOnClickListener(new  View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(), "Clciked "+textView1.getText().toString(), Toast.LENGTH_SHORT).show();

            }
        });*/


        return view;
    }
}


