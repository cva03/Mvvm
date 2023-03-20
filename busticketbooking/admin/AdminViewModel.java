package mvvmconsole.busticketbooking.admin;

import mvvmconsole.busticketbooking.dto.Bus;
import mvvmconsole.busticketbooking.repository.Repository;

import java.util.List;

public class AdminViewModel {
    private AdminView adminView;
    private Repository data= Repository.getInstance();

    public AdminViewModel(AdminView adminView) {
        this.adminView=adminView;
    }

    public void getAllBus() {
        List<Bus> busList=data.getAllBus();
        if(busList.isEmpty()){
            adminView.emptyBusList();
        }else
            adminView.showAllBus(busList);
    }


    public void addBus(Bus bus) {
        data.addBus(bus);
        adminView.busAddedSuccess(bus);
    }


    public void cancelBus(int travelId) {
        if(data.cancelBus(travelId)){
            adminView.busCancelSuccess(travelId);
        }else{
            adminView.wrongTravelId(travelId);
        }
    }

    public void printSeats(int travelId) {
        Bus bus=data.printSeats(travelId);
        adminView.printAllSeats(bus);
    }

}
