package EarTrainer;

/**
 * This is pointless to include in the StaffObject and ScoreObject classes since neither can be rendered.
 * This enforces and ensures things like Pitch and Staff are explicitly things that must be drawn.
 * It also allows for flexibility with ScoreObject/StaffObject to (eventually) represent other abstract objects
 * (e.g., TempoObject, StaffMeasure) or things that cannot explicitly be "drawn" to a canvas
 * (i.e., an actual tempo change or possibly page breaks and staff indentation).
 */
public interface Renderable
{
    void render();
}
