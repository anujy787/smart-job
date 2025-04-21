package edu.neu.csye7374.smartjob.factory;

public class JobPostFactory {
	public static JobPostCreator getCreator(String jobType) {
		return switch(jobType.toLowerCase()){
			case "full-time" -> new FullTimeJobCreator();
			case "part-time" -> new PartTimeJobCreator();
			case "remote" -> new RemoteJobCreator();
			case "internship" -> new InternshipJobCreator();
			default -> throw new IllegalArgumentException("Invalid Job Type");
		};
	}
}
