package jsonparsing.util;


public class JaroWinkler  {


	public double compute(String inputName, String  sanctionName) {
		Jaro jaro = new Jaro();
		double jaroDistance = jaro.compute(inputName, sanctionName);
		if (jaroDistance > 0.7) {
			int prefix = 0;
			for (int i = 0;
				 i < Math.min(inputName.length(), sanctionName.length()); i++) {
				if (inputName.charAt(i) == sanctionName.charAt(i))
					prefix++;
				else
					break;
			}
			prefix = Math.min(4, prefix);
			jaroDistance += 0.1 * prefix * (1 - jaroDistance);
		}
		return jaroDistance;

	}

}