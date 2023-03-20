package mvvmconsole.crmapp.admin;

import mvvmconsole.crmapp.dto.Customer;
import mvvmconsole.crmapp.dto.Interaction;
import mvvmconsole.crmapp.repository.Repository;

import java.util.List;

public class AdminModelView  {
    private AdminView adminView;
    private Repository data= Repository.getInstance();
    public AdminModelView(AdminView adminView) {
        this.adminView=adminView;
    }


    public void getAllCustomers() {
        List<Customer> customers=data.getAllCustomer();
        if(customers.isEmpty()){
            adminView.noCustomersFound();
        }
        adminView.showAllCustomers(customers);
    }


    public void getAllInteractions() {
        List<Interaction> interactions=data.getAllInteraction();
        if(interactions.isEmpty()){
            adminView.noInteractionFound();
        }
        adminView.showAllInteractions(interactions);

    }

    public void addInteraction(Interaction interaction) {
        int id=data.addInteraction(interaction);
        adminView.interactionsAddedSuccessfully(interaction.getId());
    }

    public void addCustomer(Customer customer) {
        data.addCustomer(customer);
        adminView.customerAddedSuccessfully(customer.getId());
    }


    public void checkCustomerForInteraction(int customerId) {
        if(data.checkCustomerForInteraction(customerId)){
            adminView.getInteraction(customerId);
        }else{
            adminView.interactionAddFailed();
        }
    }


    public void checkCustomerForUpdation(int id) {
        Customer customer=data.checkCustomerForUpdation(id);
        if(customer!=null){
            adminView.customerFoundForUpdation(customer);
        }else{
            adminView.customerNotFoundForUpdation(id);
        }
    }

    public void updateCustomer(int id, String name, String email, String phone) {
        data.updateCustomer(id, name,email,phone);
        adminView.customerUpdationSucessfull();
    }

    public void checkInteractionForUpdation(int id) {
        Interaction interaction=data.checkInteractionForUpdation(id);
        if(interaction!=null){
            adminView.interactionFoundForUpdation(interaction);
        }else{
            adminView.interactionNotFoundForUpdation();
        }
    }


    public void updateInteraction(int customerId, int id, String type, String notes) {
        data.updateInteraction(customerId,id,type,notes);
        adminView.InteractionUpdationSuccessfull();
    }

    public void checkInteractionForDeletion(int id) {
       Interaction interaction=data.checkInteractionForDeletion(id);
        if(interaction!=null){
            adminView.interactionFoundForDeletion(interaction);
        }else{
            adminView.interactionNotFoundForDeletion();
        }

    }

    public void checkCustomerForDeletion(int id) {
        Customer customer=data.checkCustomerForDeletion(id);
        if(customer!=null){
            adminView.customerFoundForDeletion(customer);
        }else{
            adminView.customerNotFoundForDeletion();
        }
    }

    public void deleteCustomer(int id) {
        data.deleteCustomer(id);
        adminView.customerDeletionSuccessfull();
    }


    public void deleteInteraction(int id) {
        data.deleteInteraction(id);
        adminView.interactionDeletionSuccessfull();
    }
}
