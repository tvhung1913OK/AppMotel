package com.example.duan1.fragment;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.duan1.MainActivity;
import com.example.duan1.R;
import com.example.duan1.RoomAvalibleActivity;
import com.example.duan1.database.DbMotel;
import com.example.duan1.databinding.DialogExtensionContractBinding;
import com.example.duan1.databinding.FragmentContractBinding;
import com.example.duan1.model.Contract;
import com.example.duan1.model.Invoice;
import com.example.duan1.model.RoomType;
import com.example.duan1.viewmodel.ContractViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ContractFragment extends Fragment {
    FragmentContractBinding binding;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    DbMotel db;
    ContractViewModel model;
    DialogExtensionContractBinding binding2;
    RoomType roomType;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentContractBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = DbMotel.getInstance(getContext());
        model = new ViewModelProvider(getActivity()).get(ContractViewModel.class);
        model.getContract().observe(getViewLifecycleOwner(), o -> {
            Contract contract = (Contract) o;
            binding.setContract(contract);
            roomType = db.roomTypeDao().getRTByIdContract(contract.getIdContract());
            try {
                Date dateStart = format.parse(contract.getStatingDate());
                Date dateEnd = format.parse(contract.getEndingDate());
                Long timeEnd = dateEnd.getTime() - dateStart.getTime();
                Long day = timeEnd / (1000 * 60 * 60 * 24);
                binding.setNumberEnd(day.toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            binding.btnCheckOut.setOnClickListener(view1 -> {
                Invoice invoice = db.invoiceDao().getInvoiceNotPayByIdContract(contract.getIdContract());
                if(invoice != null){
                    ((RoomAvalibleActivity)getContext()).binding.viewPager.setCurrentItem(2);
                    Toast.makeText(getContext(), "Bạn vui lòng thanh toán hết hóa đơn trước khi trả phòng!", Toast.LENGTH_SHORT).show();
                }else {
                    contract.setStatus(0);
                    db.contractDao().update(contract);
                    getActivity().finish();
                    Toast.makeText(getContext(), "Trả phòng thành công!", Toast.LENGTH_SHORT).show();
                }
            });
            binding.btnExtension.setOnClickListener(view1 -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.CustomAlertDialog);
                binding2 = DialogExtensionContractBinding.inflate(getLayoutInflater());
                builder.setView(binding2.getRoot());
                AlertDialog dialog = builder.create();
                dialog.show();
                total();
                binding2.imgNext.setOnClickListener(view2 -> {
                    String s = binding2.edNumber.getText().toString();
                    if(s!=""){
                        int number = Integer.parseInt(s) + 1;
                        binding2.edNumber.setText(number+"");
                    }
                    total();
                });
                binding2.imgPrevious.setOnClickListener(view2-> {
                    String s = binding2.edNumber.getText().toString();
                    if(s!=""){
                        int number = Integer.parseInt(s);
                        if(number > 0){
                            number --;
                        }
                        binding2.edNumber.setText(number+"");
                    }
                    total();
                });

                binding2.edNumber.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {total();}

                    @Override
                    public void afterTextChanged(Editable editable) {}
                });

                binding2.btnConfirm.setOnClickListener(view2 -> {
                    String number = binding2.edNumber.getText().toString();
                    if(number.isEmpty()){
                        Toast.makeText(getContext(), "Vui lòng không để trống", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Calendar calendar = Calendar.getInstance();
                    try {
                        calendar.setTime(format.parse(contract.getEndingDate()));
                        calendar.add(Calendar.MONTH,Integer.parseInt(number));
                        contract.setEndingDate(format.format(calendar.getTime()));
                        db.contractDao().update(contract);
                        model.setContract(contract);
                        dialog.dismiss();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                });
            });
        });
    }

    private void total() {
        String number = binding2.edNumber.getText().toString();
        if(number.isEmpty())
            binding2.tvTotal.setText("Số tiền: 0");
        else {
            int num = Integer.parseInt(number);
            int total = num*roomType.getRentCode();
            binding2.tvTotal.setText("Số tiền: "+String.format("%,d",total));
        }
    }
}