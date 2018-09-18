package brunner.jens.utils;

public class Vector2Math
{
	public static double magnitude(Vector2 vec)
	{
		return Math.sqrt(vec.x*vec.x+vec.y*vec.y);
	}
	
	public static double distance(Vector2 vec1, Vector2 vec2)
	{
		return Math.abs(magnitude(subtract(vec1, vec2)));
	}
	
	public static double distance2(Vector2 vec1, Vector2 vec2)
	{
		Vector2 diff = subtract(vec1, vec2);
		return diff.x*diff.x+diff.y*diff.y;
	}
	
	public static Vector2 subtract(Vector2 vec1, Vector2 vec2)
	{
		return new Vector2(vec1.x - vec2.x, vec1.y - vec2.y);
	}
	
	public static Vector2 add(Vector2 vec1, Vector2 vec2)
	{
		return new Vector2(vec1.x + vec2.x, vec1.y + vec2.y);
	}
	
	//Multiply a vector by a scalar
	public static Vector2 mult(Vector2 vec1, double factor)
	{
		return new Vector2(vec1.x * factor, vec1.y * factor);
	}
	
	
	/*public static Vector2 scale(Vector2 vec, double scalar) This function is never used in the code*/

}
