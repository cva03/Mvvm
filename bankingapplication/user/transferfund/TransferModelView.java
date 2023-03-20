package mvvmconsole.bankingapplication.user.transferfund;

import mvvmconsole.bankingapplication.dto.Account;
import mvvmconsole.bankingapplication.repository.Repository;

public class TransferModelView {

    private TransferView transferView;
    private Repository data= Repository.getInstance();

    public TransferModelView(TransferView transferView) {
        this.transferView=transferView;
    }

    public void checkTransferAccount(int accNo) {
        String name=data.checkTransferAcc(accNo);
        if(!name.equals(" ")){
            transferView.transferAccFound(name,accNo);
        }else {
            transferView.transferAccNotFound();
        }
    }

    public void checkAmount(long amount, int accNo, Account account) {
        if(data.sendAmount(amount,accNo,account)){
            transferView.amountSuccessfullySend(amount,accNo);
        }else{
            transferView.inSufficientFunds();
        }

    }
}
