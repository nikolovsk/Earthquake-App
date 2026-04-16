import type { Earthquake } from "../types/earthquake"

interface Props {
    earthquakes: Earthquake[]
}

export default function EarthquakeTable({ earthquakes }: Props) {
    return (
        <table
            border={1}
            cellPadding={8}
            style={{
                borderCollapse: "collapse",
                width: "100%"
            }}
        >
            <thead>
            <tr>
                <th>Magnitude</th>
                <th>Mag Type</th>
                <th>Place</th>
                <th>Title</th>
                <th>Time</th>
            </tr>
            </thead>

            <tbody>
            {earthquakes.map(e => (
                <tr key={e.id}>
                    <td>{e.magnitude}</td>
                    <td>{e.magType}</td>
                    <td>{e.place}</td>
                    <td>{e.title}</td>
                    <td>{new Date(e.time).toLocaleString()}</td>
                </tr>
            ))}
            </tbody>
        </table>
    )
}