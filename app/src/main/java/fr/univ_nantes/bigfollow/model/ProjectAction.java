package fr.univ_nantes.bigfollow.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import org.json.JSONObject;

import java.util.Date;
import java.util.List;

import fr.univ_nantes.bigfollow.util.Util;

@Table(name = "ProjectAction")
public class ProjectAction extends Model {

    public static final int INVOICING_TYPE_TP = 1;
    public static final int INVOICING_TYPE_FO = 2;
    public static final int STEP_NULL = -1;
    private static final String TP = "TP";
    private static final String FO = "FO";
    private static final String NO_STEP = "AUCUN";

    @Column(name = "Type")
    private String type;
    @Column(name = "Priority")
    private String order;
    @Column(name = "Price")
    private String price;
    @Column(name = "Domain")
    private String domain;
    @Column(name = "Step")
    private int step;
    @Column(name = "Action", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private String action;
    @Column(name = "IsDisplayed")
    private boolean isDisplayed;
    @Column(name = "InvoicingType")
    private int invoicingType;
    @Column(name = "DaysCount")
    private String daysCount;
    @Column(name = "StartAt")
    private String startAt;
    @Column (name = "EndAt")
    private String endAt;
    @Column(name = "CostByDay")
    private String costByDay;
    @Column(name = "ProjectManager")
    private String projectManager;
    @Column(name = "ProductOwner")
    private String productOwner;
    @Column(name = "ProductUser")
    private String productUser;

    public ProjectAction() {
        super();
    }

    public static List<ProjectAction> getByIdProject(long idProject) {
        return new Select()
                .from(ProjectAction.class)
                .where("Project = ?", idProject)
                .orderBy("Priority ASC")
                .execute();
    }
    public static List<ProjectAction> getAll() {
        return new Select()
                .from(ProjectAction.class)
                .orderBy("Priority ASC")
                .execute();
    }

    public static ProjectAction convertToProjectAction(List<String> l) {
        int lSize = l.size();
        ProjectAction pa = new ProjectAction();

        pa.setType(l.get(0));
        pa.setOrder(l.get(1));
        pa.setPrice(l.get(2));
        pa.setDomain(l.get(3));

        String step = l.get(4);
        if (!step.isEmpty() && !step.equals(NO_STEP)) {
            pa.setStep(Integer.valueOf(step));
        } else {
            pa.setStep(STEP_NULL);
        }

        pa.setAction(l.get(5));
        pa.setDisplayed(Boolean.valueOf(l.get(6)));
        pa.setInvoicingType((l.get(7)).equals(FO) ? INVOICING_TYPE_FO : INVOICING_TYPE_TP);
        pa.setDaysCount(lSize >= 9 ? l.get(8) : null);
        pa.setStartAt(lSize >= 10 ? l.get(9) : null);
        pa.setEndAt(lSize >= 11 ? l.get(10) : null);
        pa.setCostByDay(lSize >= 12 ? l.get(11) : null);
        pa.setProjectManager(lSize >= 13 ? l.get(12) : null);
        pa.setProductOwner(lSize >= 14 ? l.get(13) : null);
        pa.setProductUser(lSize >= 15 ? l.get(14) : null);

        return pa;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public boolean isDisplayed() {
        return isDisplayed;
    }

    public void setDisplayed(boolean displayed) {
        isDisplayed = displayed;
    }

    public int getInvoicingType() {
        return invoicingType;
    }

    public void setInvoicingType(int invoicingType) {
        this.invoicingType = invoicingType;
    }

    public String getDaysCount() {
        return daysCount;
    }

    public void setDaysCount(String daysCount) {
        this.daysCount = daysCount;
    }

    public String getCostByDay() {
        return costByDay;
    }

    public void setCostByDay(String costByDay) {
        this.costByDay = costByDay;
    }

    public String getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }

    public String getProductOwner() {
        return productOwner;
    }

    public void setProductOwner(String productOwner) {
        this.productOwner = productOwner;
    }

    public String getProductUser() {
        return productUser;
    }

    public void setProductUser(String productUser) {
        this.productUser = productUser;
    }

    public String getStartAt() {
        return startAt;
    }

    public void setStartAt(String startAt) {
        this.startAt = startAt;
    }

    public String getEndAt() {
        return endAt;
    }

    public void setEndAt(String endAt) {
        this.endAt = endAt;
    }
}
