package brunner.jens.utils;

public class Vector2Math
{
	public static float magnitude(Vector2 vector)
	{
		return (float)Math.sqrt(Math.pow(vector.x, 2.0)+Math.pow(vector.y, 2.0));
	}
	
	public static float distance(Vector2 vec1, Vector2 vec2)
	{
		return Math.abs(magnitude(subtract(vec1, vec2)));
	}
	
	public static Vector2 subtract(Vector2 vec1, Vector2 vec2)
	{
		return new Vector2(vec1.x - vec2.x, vec1.y - vec2.y);
	}
	
	public static Vector2 add(Vector2 vec1, Vector2 vec2)
	{
		return new Vector2(vec1.x + vec2.x, vec1.y + vec2.y);
	}
	
	public static Vector2 mult(Vector2 vec1, float factor)
	{
		return new Vector2(vec1.x * factor, vec1.y * factor);
	}
}
