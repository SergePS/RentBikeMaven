package com.postnikov.epam.command;

import com.postnikov.epam.command.impl.AddBikeCommand;
import com.postnikov.epam.command.impl.AddBikeProductCommand;
import com.postnikov.epam.command.impl.AddBikeTypeCommand;
import com.postnikov.epam.command.impl.AddBrandCommand;
import com.postnikov.epam.command.impl.AddParkingCommand;
import com.postnikov.epam.command.impl.ChangeLocalizationCommand;
import com.postnikov.epam.command.impl.ChooseBikeCommand;
import com.postnikov.epam.command.impl.ChooseBikeProductCommand;
import com.postnikov.epam.command.impl.CloseOrderCommand;
import com.postnikov.epam.command.impl.CreateOrderCommand;
import com.postnikov.epam.command.impl.FindBikeCommand;
import com.postnikov.epam.command.impl.FindBikeProductCommand;
import com.postnikov.epam.command.impl.FindOrderByParametersCommand;
import com.postnikov.epam.command.impl.FindUserOrdersCommand;
import com.postnikov.epam.command.impl.GoToAddBikePageCommand;
import com.postnikov.epam.command.impl.GoToAddParkingPageCommand;
import com.postnikov.epam.command.impl.GoToBikeCatalogPageCommand;
import com.postnikov.epam.command.impl.GoToBikeProductCatalogPageCommand;
import com.postnikov.epam.command.impl.GoToBikePurchasePageCommand;
import com.postnikov.epam.command.impl.GoToHomePageCommand;
import com.postnikov.epam.command.impl.GoToOrderPageCommand;
import com.postnikov.epam.command.impl.GoToOrderReportPage;
import com.postnikov.epam.command.impl.GoToParkingPageCommand;
import com.postnikov.epam.command.impl.LoginCommand;
import com.postnikov.epam.command.impl.LogoutCommand;
import com.postnikov.epam.command.impl.PagingCommand;
import com.postnikov.epam.command.impl.RegisterUserCommand;
import com.postnikov.epam.command.impl.TakeAllUserCommand;
import com.postnikov.epam.command.impl.UpdateBikeCommand;
import com.postnikov.epam.command.impl.UpdateParkingCommand;
import com.postnikov.epam.command.impl.UpdatePasswordCommand;
import com.postnikov.epam.command.impl.UpdateUserCommand;
import com.postnikov.epam.rentbike.domain.entity.AccessLevel;

/**
 * @author Sergey Postnikov
 *
 */
/**
 * @author SergeWork
 *
 */
public enum CommandType {

	LOGIN(new LoginCommand(), AccessLevel.USER),
	LOGOUT(new LogoutCommand(), AccessLevel.USER),
	REGISTER(new RegisterUserCommand(), AccessLevel.USER),
	HOME(new GoToHomePageCommand(), AccessLevel.USER),
	TAKE_ALL_USER(new TakeAllUserCommand(), AccessLevel.ADMIN),
	UPDATE_USER(new UpdateUserCommand(), AccessLevel.USER),
	UPDATE_PASSWORD(new UpdatePasswordCommand(), AccessLevel.USER),
	
	CHANGE_LOCALIZATION(new ChangeLocalizationCommand(), AccessLevel.USER),
	
	ADD_BIKE(new AddBikeCommand(), AccessLevel.ADMIN),
	ADD_BRAND(new AddBrandCommand(), AccessLevel.ADMIN),
	ADD_BIKE_TYPE(new AddBikeTypeCommand(), AccessLevel.ADMIN),
	BIKECATALOG(new GoToBikeCatalogPageCommand(), AccessLevel.USER),
	FIND_BIKE(new FindBikeCommand(), AccessLevel.USER),
	GO_TO_ADD_BIKE_PAGE(new GoToAddBikePageCommand(), AccessLevel.ADMIN),
	GO_TO_BIKE_PURCHASE(new GoToBikePurchasePageCommand(), AccessLevel.ADMIN),
	UPDATE_BIKE(new UpdateBikeCommand(), AccessLevel.ADMIN),
	CHOOSE_BIKE(new ChooseBikeCommand(), AccessLevel.USER),
	ADD_BIKE_PRODUCT(new AddBikeProductCommand(), AccessLevel.ADMIN),

	ADD_PARKING(new AddParkingCommand(), AccessLevel.ADMIN),
	GO_TO_PARKING_PAGE(new GoToParkingPageCommand(), AccessLevel.ADMIN),
	GO_TO_ADD_PARKING_PAGE(new GoToAddParkingPageCommand(), AccessLevel.ADMIN),
	UPDATE_PARKING(new UpdateParkingCommand(), AccessLevel.ADMIN),
	
	GO_TO_BIKE_PRODUCT_CATALOG_PAGE(new GoToBikeProductCatalogPageCommand(), AccessLevel.USER),
	FIND_BIKE_PRODUCT(new FindBikeProductCommand(), AccessLevel.USER),
	CHOOSE_BIKE_PRODUCT(new ChooseBikeProductCommand(), AccessLevel.USER),

	GO_TO_ORDER_PAGE(new GoToOrderPageCommand(), AccessLevel.USER),
	CREATE_ORDER(new CreateOrderCommand(), AccessLevel.USER),
	CLOSE_ORDER(new CloseOrderCommand(), AccessLevel.USER),
	FIND_USER_ORDERS(new FindUserOrdersCommand(), AccessLevel.USER),
	GO_TO_ORDER_REPORT_PAGE(new GoToOrderReportPage(), AccessLevel.ADMIN),
	FIND_ORDER_BY_PARAMETERS(new FindOrderByParametersCommand(), AccessLevel.ADMIN),
	
	PAGINATION(new PagingCommand(), AccessLevel.USER);
	
	
	/** Command object */
	private Command command;
	
	/** Access level for page */
	private AccessLevel accessLevel;
	
	private CommandType(Command command, AccessLevel accessLevel){
		this.command = command;
		this.accessLevel = accessLevel;
	}
	
	public Command getCommand() {
		return command;
	}

	public AccessLevel getAccessLevel() {
		return accessLevel;
	}

}
