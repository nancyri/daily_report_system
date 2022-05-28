package models.validators;

import java.util.ArrayList;
import java.util.List;

import constants.MessageConst;
import services.EmployeeService;
import views.EmployeeView;

public class EmployeeValidator {

	public static List<String> validate(EmployeeService service, EmployeeView ev, Boolean codeDuplicateCheckFlag, Boolean passwordCheckFlag) {
        List<String> errors = new ArrayList<String>();


	String codeError = validateCode(service, ev.getCode(), codeDuplicateCheckFlag);
    if (!codeError.equals("")) {
        errors.add(codeError);
    }

    String nameError = validateName(ev.getName());
    if (!nameError.equals("")) {
        errors.add(nameError);
    }

    String passError = validatePassword(ev.getPassword(), passwordCheckFlag);
    if (!passError.equals("")) {
        errors.add(passError);
    }

    return errors;

	}


    private static String validateCode(EmployeeService service, String code, Boolean codeDuplicateCheckFlag) {


        if (code == null || code.equals("")) {
            return MessageConst.E_NOEMP_CODE.getMessage();
        }

        if (codeDuplicateCheckFlag) {

            long employeesCount = isDuplicateEmployee(service, code);

            if (employeesCount > 0) {
                return MessageConst.E_EMP_CODE_EXIST.getMessage();
            }
        }

        //エラーがない場合は空文字を返却
        return "";
    }


    private static long isDuplicateEmployee(EmployeeService service, String code) {

        long employeesCount = service.countByCode(code);
        return employeesCount;
    }


    private static String validateName(String name) {

        if (name == null || name.equals("")) {
            return MessageConst.E_NONAME.getMessage();
        }

        //入力値がある場合は空文字を返却
        return "";
    }

    /**
     * パスワードの入力チェックを行い、エラーメッセージを返却
     * @param password パスワード
     * @param passwordCheckFlag パスワードの入力チェックを実施するかどうか(実施する:true 実施しない:false)
     * @return エラーメッセージ
     */
    private static String validatePassword(String password, Boolean passwordCheckFlag) {

        //入力チェックを実施 かつ 入力値がなければエラーメッセージを返却
        if (passwordCheckFlag && (password == null || password.equals(""))) {
            return MessageConst.E_NOPASSWORD.getMessage();
        }

        //エラーがない場合は空文字を返却
        return "";
    }
}



