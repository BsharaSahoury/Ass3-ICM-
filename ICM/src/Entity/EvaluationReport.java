package Entity;

public class EvaluationReport {
private String location;
private String description;
private String expectedResult;
private String constraints;
private String risks;
private String estimatedPerfomanceDuration;

public EvaluationReport(String location, String description, String expectedResult, String constraints, String risks,
		String estimatedPerfomanceDuration) {
	this.location = location;
	this.description = description;
	this.expectedResult = expectedResult;
	this.constraints = constraints;
	this.risks = risks;
	this.estimatedPerfomanceDuration = estimatedPerfomanceDuration;
}
public String getLocation() {
	return location;
}
public void setLocation(String location) {
	this.location = location;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
public String getExpectedResult() {
	return expectedResult;
}
public void setExpectedResult(String expectedResult) {
	this.expectedResult = expectedResult;
}
public String getConstraints() {
	return constraints;
}
public void setConstraints(String constraints) {
	this.constraints = constraints;
}
public String getRisks() {
	return risks;
}
public void setRisks(String risks) {
	this.risks = risks;
}
public String getEstimatedPerfomanceDuration() {
	return estimatedPerfomanceDuration;
}
public void setEstimatedPerfomanceDuration(String estimatedPerfomanceDuration) {
	this.estimatedPerfomanceDuration = estimatedPerfomanceDuration;
}

}
